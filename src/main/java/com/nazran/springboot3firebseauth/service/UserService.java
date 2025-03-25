package com.nazran.springboot3firebseauth.service;

import com.nazran.springboot3firebseauth.dto.UserDto;
import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.response.UserResponse;

import java.util.Map;
import java.util.Optional;

/**
 * Service interface for managing user-related operations.
 * Provides methods for adding, retrieving, updating, and searching users.
 */
public interface UserService {

    /**
     * Adds a new user based on the provided user data.
     *
     * @param dto the {@link UserDto} containing user details to be added
     * @return a {@link UserResponse} representing the result of the add operation
     */
    UserResponse addUser(UserDto dto);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to find
     * @return a {@link UserResponse} containing the user's details
     */
    UserResponse findByEmail(String email);

    /**
     * Checks if a user exists by their email address and returns an optional user entity.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found
     */
    Optional<User> findByEmailExist(String email);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return a {@link UserResponse} containing the user's details
     */
    UserResponse findById(Integer id);

    /**
     * Updates an existing user's information based on the provided data.
     *
     * @param dto the {@link UserDto} containing updated user details
     * @return a {@link UserResponse} representing the result of the update operation
     */
    UserResponse update(UserDto dto);

    /**
     * Searches for users matching the provided email with pagination and sorting options.
     *
     * @param email the email address to search for (can be a partial match)
     * @param page the page number for pagination (zero-based)
     * @param size the number of records per page
     * @param sortBy the field to sort the results by
     * @return a {@link Map} containing the search results and metadata (e.g., total count, user list)
     */
    Map<String, Object> searchUserList(String email, Integer page, Integer size, String sortBy);
}