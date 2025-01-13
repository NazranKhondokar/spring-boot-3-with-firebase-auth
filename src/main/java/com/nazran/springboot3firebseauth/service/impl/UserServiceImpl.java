package com.nazran.springboot3firebseauth.service.impl;

import com.nazran.springboot3firebseauth.entity.QUser;
import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.repository.UserRepository;
import com.nazran.springboot3firebseauth.response.UserResponse;
import com.nazran.springboot3firebseauth.service.UserService;
import com.nazran.springboot3firebseauth.utils.ServiceHelper;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return UserResponse.from(user);
    }

    @Override
    public Optional<User> findByEmailExist(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Map<String, Object> searchUserList(String email, Integer page, Integer size, String sortBy) {
        QUser user = QUser.user;
        // Build QueryDSL Predicate
        Predicate predicate = user.email.containsIgnoreCase(email);
        // Use QueryDSL to fetch paginated results
        ServiceHelper<User> helper = new ServiceHelper<>(User.class);
        Page<User> result = userRepository.findAll(predicate, helper.getPageable(sortBy, page, size));

        // Prepare paginated response
        return helper.getList(result, page, size);
    }
}
