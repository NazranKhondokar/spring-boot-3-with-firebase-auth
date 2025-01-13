package com.nazran.springboot3firebseauth.controller;

import com.nazran.springboot3firebseauth.dto.LoginRequest;
import com.nazran.springboot3firebseauth.dto.UserRegistrationRequest;
import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.exception.CustomMessagePresentException;
import com.nazran.springboot3firebseauth.response.LoginResponse;
import com.nazran.springboot3firebseauth.response.UserRegistrationResponse;
import com.nazran.springboot3firebseauth.service.AuthService;
import com.nazran.springboot3firebseauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.nazran.springboot3firebseauth.constant.ValidatorConstants.ALREADY_EXIST;
import static com.nazran.springboot3firebseauth.utils.ResponseBuilder.error;
import static com.nazran.springboot3firebseauth.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for user registration and email verification.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "API for user operations")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Registers a user and sends an email verification link.")
    @PostMapping("/register")
    public ResponseEntity<JSONObject> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        Optional<User> users = userService.findByEmailExist(request.getEmail());
        if (users.isPresent())
            throw new CustomMessagePresentException("This email " + ALREADY_EXIST);
        UserRegistrationResponse response = authService.registerUser(request);
        return ok(success(response, "User registered successfully. Email verification sent.").getJson());
    }

    @Operation(summary = "Resend email verification link", description = "Resends a verification email to an existing user.")
    @PostMapping("/resend-verification")
    public ResponseEntity<JSONObject> resendVerificationEmail(@RequestParam String email) {
        try {
            String verificationLink = authService.resendVerificationEmail(email);
            logger.info("Verification email link successfully generated for: {}", email);
            return ok(success(verificationLink).getJson());
        } catch (CustomMessagePresentException ex) {
            logger.error("Error while resending verification email for {}: {}", email, ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(error(null, ex.getMessage()).getJson());
        } catch (Exception ex) {
            logger.error("Unexpected error while resending verification email for {}: {}", email, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error(null, "Unexpected error occurred").getJson());
        }
    }

    @Operation(summary = "Authenticate user", description = "Handles user login")
    @PostMapping("/login")
    public ResponseEntity<JSONObject> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = authService.login(request.getIdToken());
            logger.info("Social login successfully processed for ID token.");
            return ok(success(loginResponse, "User successfully authenticated and saved.").getJson());
        } catch (CustomMessagePresentException ex) {
            logger.error("Error during social login: {}", ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(error(null, ex.getMessage()).getJson());
        }
    }
}