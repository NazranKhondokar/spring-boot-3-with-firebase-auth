package com.nazran.springboot3firebseauth.service.impl;

import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.enums.AvatarType;
import com.nazran.springboot3firebseauth.exception.CustomMessagePresentException;
import com.nazran.springboot3firebseauth.repository.UserRepository;
import com.nazran.springboot3firebseauth.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service for managing user interests.
 */
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void setAvatar(Integer avatarType, MultipartFile file, String firebaseUserId) throws IOException {
        // Validate input
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        if (avatarType < 0 || avatarType >= AvatarType.values().length) {
            throw new IllegalArgumentException("Invalid avatar type.");
        }

        // Retrieve user
        User user = userRepository.findByFirebaseUserId(firebaseUserId)
                .orElseThrow(() -> new CustomMessagePresentException("User not exists by Firebase User Id"));

        // Update user fields
        user.setAvatarType(AvatarType.values()[avatarType]);
        user.setAvatar(file.getBytes());

        // Save user
        userRepository.save(user);
        logger.info("Avatar updated successfully for userId: {}", firebaseUserId);
    }
}