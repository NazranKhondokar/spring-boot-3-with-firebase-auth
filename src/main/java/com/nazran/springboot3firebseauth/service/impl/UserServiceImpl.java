package com.nazran.springboot3firebseauth.service.impl;

import com.nazran.springboot3firebseauth.dto.UserDto;
import com.nazran.springboot3firebseauth.entity.QUser;
import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.exception.CustomMessagePresentException;
import com.nazran.springboot3firebseauth.repository.UserRepository;
import com.nazran.springboot3firebseauth.response.UserResponse;
import com.nazran.springboot3firebseauth.service.UserService;
import com.nazran.springboot3firebseauth.utils.ImageUtils;
import com.nazran.springboot3firebseauth.utils.ServiceHelper;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ImageUtils imageUtils;

    @Value("${USER_JOIN_DATE_FORMAT}")
    private String userJoinDateFormat;

    @Override
    @Transactional
    public UserResponse addUser(UserDto dto) {
        User user = dto.to();
        //helper.getSaveData(user);
        return UserResponse.from(userRepository.save(user));
    }

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
    public UserResponse findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomMessagePresentException("User not found: " + id));
        return UserResponse.from(user);
    }

    @Override
    public UserResponse update(UserDto dto) {
        User users = userRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomMessagePresentException("User not exists by Id"));
        users = dto.update(users);
        return UserResponse.from(userRepository.save(users));
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
