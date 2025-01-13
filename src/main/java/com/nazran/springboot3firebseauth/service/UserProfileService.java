package com.nazran.springboot3firebseauth.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service for managing user interests.
 */
public interface UserProfileService {

    /**
     * Sets the avatar for the specified user.
     *
     * @param avatarType     the type of avatar being set (e.g., predefined or custom).
     * @param file           the file containing the avatar image.
     * @param firebaseUserId the unique identifier of the user in Firebase.
     * @throws IOException if an error occurs while processing the avatar file.
     */
    void setAvatar(Integer avatarType, MultipartFile file, String firebaseUserId) throws IOException;
}

