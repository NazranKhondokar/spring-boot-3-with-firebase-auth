package com.nazran.springboot3firebseauth.repository;

import com.nazran.springboot3firebseauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFirebaseUserId(String firebaseUserId);
}
