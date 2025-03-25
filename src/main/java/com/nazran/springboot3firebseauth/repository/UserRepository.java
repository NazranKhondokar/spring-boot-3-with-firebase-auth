package com.nazran.springboot3firebseauth.repository;

import com.nazran.springboot3firebseauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * Provides CRUD operations and custom query methods for user management.
 * Extends {@link JpaRepository} for JPA functionality and
 * {@link QuerydslPredicateExecutor} for type-safe queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their Firebase User ID.
     *
     * @param firebaseUserId the Firebase User ID to search for
     * @return an {@link Optional} containing the user if found, empty otherwise
     */
    Optional<User> findByFirebaseUserId(String firebaseUserId);

    /**
     * Checks whether a user exists with the given email address.
     *
     * @param email the email address to check
     * @return true if a user exists with the given email, false otherwise
     */
    boolean existsByEmail(String email);
}