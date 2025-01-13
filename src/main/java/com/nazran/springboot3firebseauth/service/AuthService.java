package com.nazran.springboot3firebseauth.service;

import com.nazran.springboot3firebseauth.dto.UserRegistrationRequest;
import com.nazran.springboot3firebseauth.response.LoginResponse;
import com.nazran.springboot3firebseauth.response.UserRegistrationResponse;

/**
 * Service interface for user registration.
 */
public interface AuthService {

    /**
     * Registers a new user and sends a verification email.
     *
     * @param request User registration request data.
     * @return User registration response.
     */
    UserRegistrationResponse registerUser(UserRegistrationRequest request);

    /**
     * Sends an email verification link to an existing user.
     *
     * @param email Email of the user.
     * @return email verification link
     */
    String resendVerificationEmail(String email);

    /**
     * @param idToken is consist of `idToken`
     * @return LoginResponse
     */
    LoginResponse login(String idToken);
}

