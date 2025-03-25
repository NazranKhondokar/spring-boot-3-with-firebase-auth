package com.nazran.springboot3firebseauth.service.impl;

import com.nazran.springboot3firebseauth.repository.UserRepository;
import com.nazran.springboot3firebseauth.service.CheckEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service implementation for Check if Email is Already Registered.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheckEmailServiceImpl implements CheckEmailService {
    private static final Logger logger = LoggerFactory.getLogger(CheckEmailServiceImpl.class);

    private final UserRepository userRepository;

    /**
     * Check if Email is Already Registered.
     *
     * @param email Email of the user.
     * @return exists true or false
     */
    @Override
    public boolean emailExists(String email) {
        logger.info("Checking if email exists: {}", email);
        return userRepository.existsByEmail(email);
    }
}