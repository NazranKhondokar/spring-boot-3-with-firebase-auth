package com.nazran.springboot3firebseauth.service.impl;

import com.nazran.springboot3firebseauth.dto.UpdateUserRequest;
import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.enums.ReferenceType;
import com.nazran.springboot3firebseauth.exception.CustomMessagePresentException;
import com.nazran.springboot3firebseauth.repository.UserRepository;
import com.nazran.springboot3firebseauth.response.CurrentUserResponse;
import com.nazran.springboot3firebseauth.response.MediaStorageResponse;
import com.nazran.springboot3firebseauth.service.CurrentUserService;
import com.nazran.springboot3firebseauth.service.FirebaseStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Implementation of the CurrentUserService interface.
 */
@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private static final Logger logger = LoggerFactory.getLogger(CurrentUserServiceImpl.class);
    private final UserRepository userRepository;
    private final FirebaseStorageService storageService;

    @Value("${USER_JOIN_DATE_FORMAT}")
    private String userJoinDateFormat;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void setAvatar(MultipartFile file, String firebaseUserId) throws IOException {
        // Validate input
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        // Retrieve user
        User user = findByFirebaseUserIdOrThrow(firebaseUserId);

        // Prepare file metadata
        Integer referenceId = user.getId();  // The user ID

        // Upload file to Firebase Storage
        storageService.uploadFile(file, user.getId(), ReferenceType.UsersAvatars, referenceId);

        // Save user
        userRepository.save(user);
        logger.info("Avatar updated successfully for userId: {}", firebaseUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CurrentUserResponse getCurrentUser(String firebaseUserId) {
        logger.info("Fetching user data for Firebase User ID: {}", firebaseUserId);

        User user = findByFirebaseUserIdOrThrow(firebaseUserId);

        // Prepare response
        CurrentUserResponse response = new CurrentUserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setIsEmailVerified(user.getIsEmailVerified());
        response.setStatus(user.getUserStatus().name());
        response.setFirebaseUserId(user.getFirebaseUserId());
        response.setJoinDate(user.getCreatedAt().format(DateTimeFormatter.ofPattern(userJoinDateFormat)));

        Optional.ofNullable(storageService.getMediaStorage(user.getId(), ReferenceType.UsersAvatars))
                .map(MediaStorageResponse::getUrl)
                .ifPresent(response::setAvatar);

        logger.info("Successfully fetched user data for Firebase User ID: {}", firebaseUserId);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateCurrentUser(String firebaseUserId, UpdateUserRequest request) {
        logger.info("Updating user data for Firebase User ID: {}", firebaseUserId);

        User user = findByFirebaseUserIdOrThrow(firebaseUserId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);
        logger.info("Successfully updated user data for Firebase User ID: {}", firebaseUserId);
    }

    /**
     * Retrieve user by Firebase User ID or throw an exception.
     */
    private User findByFirebaseUserIdOrThrow(String firebaseUserId) {
        return userRepository.findByFirebaseUserId(firebaseUserId)
                .orElseThrow(() -> new CustomMessagePresentException("User does not exist for this Firebase User Id"));
    }
}
