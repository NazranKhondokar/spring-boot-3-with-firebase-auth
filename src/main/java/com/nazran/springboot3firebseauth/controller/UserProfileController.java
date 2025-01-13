package com.nazran.springboot3firebseauth.controller;

import com.nazran.springboot3firebseauth.annotation.ValidAvatar;
import com.nazran.springboot3firebseauth.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.nazran.springboot3firebseauth.utils.ResponseBuilder.error;
import static com.nazran.springboot3firebseauth.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for managing user interests.
 */
@RestController
@RequestMapping("/api/v1/current-user")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Endpoints for managing user profile")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    private final UserProfileService userProfileService;

    /**
     * Endpoint to upload and set the avatar for the authenticated user.
     *
     * @param avatarType     The type of avatar
     * @param file           The avatar file to upload. Must be a JPEG/PNG file and not exceed 5MB.
     * @param firebaseUserId The Firebase User ID of the authenticated user.
     * @return ResponseEntity  A response indicating the result of the operation.
     * @throws IllegalArgumentException If validation errors occur during the request.
     */
    @PostMapping(value = "/set-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Set user avatar",
            description = "Allows authenticated users to set their avatar. Supported avatar types: " +
                    "0: Standard, 1: Red Eyes, 2: Custom. File must be JPEG/PNG and not exceed 5MB.")
    public ResponseEntity<JSONObject> setAvatar(
            @NotNull(message = "Avatar type is required.")
            @Min(value = 0, message = "Avatar type must be at least 0.")
            @Max(value = 2, message = "Avatar type must be at most 2.")
            @RequestParam("avatarType") Integer avatarType,

            @ValidAvatar
            @RequestParam("file") MultipartFile file,

            @AuthenticationPrincipal String firebaseUserId) {
        logger.info("Processing avatar upload request for user with Firebase ID: {} and avatar type: {}", firebaseUserId, avatarType);
        try {
            userProfileService.setAvatar(avatarType, file, firebaseUserId);
            logger.info("Successfully set avatar for user with Firebase ID: {}", firebaseUserId);
            return ok(success(null, "Avatar uploaded successfully.").getJson());
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error while setting avatar for user with Firebase ID {}: {}", firebaseUserId, e.getMessage());
            return ResponseEntity.badRequest().body(error(null, e.getMessage()).getJson());
        } catch (IOException e) {
            logger.error("IO error while processing avatar for user with Firebase ID {}: {}", firebaseUserId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(error(null, e.getMessage()).getJson());
        }
    }
}