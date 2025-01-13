package com.nazran.springboot3firebseauth.service;

import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.response.UserResponse;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    UserResponse findByEmail(String email);

    Optional<User> findByEmailExist(String email);

    Map<String, Object> searchUserList(String email, Integer page, Integer size, String sortBy);
}
