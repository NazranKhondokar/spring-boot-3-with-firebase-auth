package com.nazran.springboot3firebseauth.controller;

import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.response.UserResponse;
import com.nazran.springboot3firebseauth.service.UserService;
import com.nazran.springboot3firebseauth.utils.CommonDataHelper;
import com.nazran.springboot3firebseauth.utils.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nazran.springboot3firebseauth.utils.ResponseBuilder.paginatedSuccess;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Users")
public class UsersController {

    private final UserService service;
    private final CommonDataHelper commonDataHelper;

    @GetMapping("/list")
    @Operation(summary = "block menu list", description = "block menu list")
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