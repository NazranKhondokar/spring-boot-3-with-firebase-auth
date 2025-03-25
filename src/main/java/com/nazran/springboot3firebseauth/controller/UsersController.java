package com.nazran.springboot3firebseauth.controller;

import com.nazran.springboot3firebseauth.dto.UserDto;
import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.exception.ResourceNotFoundException;
import com.nazran.springboot3firebseauth.response.CurrentUserResponse;
import com.nazran.springboot3firebseauth.response.UserResponse;
import com.nazran.springboot3firebseauth.service.CurrentUserService;
import com.nazran.springboot3firebseauth.service.UserService;
import com.nazran.springboot3firebseauth.utils.CommonDataHelper;
import com.nazran.springboot3firebseauth.utils.PaginatedResponse;
import com.nazran.springboot3firebseauth.validation.UsersValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nazran.springboot3firebseauth.constant.MessageConstants.ADD_USER;
import static com.nazran.springboot3firebseauth.constant.MessageConstants.UPDATE_USER;
import static com.nazran.springboot3firebseauth.exception.ApiError.fieldError;
import static com.nazran.springboot3firebseauth.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersValidator validator;
    private final UserService service;
    private final CurrentUserService currentUserService;
    private final CommonDataHelper commonDataHelper;

    @PostMapping
    @Operation(summary = "User add", description = "User add")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    public ResponseEntity<JSONObject> addUser(@Valid @RequestBody UserDto dto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        service.addUser(dto);
        return ok(success(null, ADD_USER).getJson());
    }


    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to fetch.
     * @return ResponseEntity containing user details if found, or an error response if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID",
            description = "Retrieves the current user details based on the common user identifier.")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CurrentUserResponse.class))})
    public ResponseEntity<JSONObject> findById(@Valid @PathVariable @NotNull Integer id) {

        logger.info("Received request to fetch user with ID: {}", id);
        try {
            // Fetch the user by ID using the common logic (returns a UserResponse)
            UserResponse userResponse = service.findById(id);
            // Retrieve current user details based on the Firebase user ID
            CurrentUserResponse response = currentUserService.getCurrentUser(userResponse.getFirebaseUserId());
            return ok(success(response).getJson());

        } catch (ResourceNotFoundException e) {
            logger.warn("User with ID {} not found: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(error(null, e.getMessage()).getJson());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(error(null, e.getMessage()).getJson());
        }
    }


    @PutMapping
    @Operation(summary = "Update user by id", description = "Update existing user by id")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    public ResponseEntity<JSONObject> updateBlockMenu(@Valid @RequestBody UserDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        UserResponse response=service.update(dto);
        return ok(success(response, UPDATE_USER).getJson());
    }

    @GetMapping
    @Operation(summary = "Retrieve a list of users", description = "Retrieve a list of users with optional filters for email, page, and size.")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "email", defaultValue = "") String email
    ) {
        Map<String, Object> userMenuMap = service.searchUserList(email, page, size, sortBy);
        PaginatedResponse response = new PaginatedResponse();
        List<User> responses = (List<User>) userMenuMap.get("lists");
        List<UserResponse> customResponses = responses.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
        commonDataHelper.getCommonData(page, size, userMenuMap, response, customResponses);
        return ok(paginatedSuccess(response).getJson());
    }
}