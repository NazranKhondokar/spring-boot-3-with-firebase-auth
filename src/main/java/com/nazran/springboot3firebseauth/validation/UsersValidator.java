package com.nazran.springboot3firebseauth.validation;

import com.nazran.springboot3firebseauth.dto.UserDto;
import com.nazran.springboot3firebseauth.exception.CustomMessagePresentException;
import com.nazran.springboot3firebseauth.response.UserResponse;
import com.nazran.springboot3firebseauth.service.UserService;
import com.nazran.springboot3firebseauth.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.nazran.springboot3firebseauth.constant.ValidatorConstants.ALREADY_EXIST;

@Component
@RequiredArgsConstructor
public class UsersValidator implements Validator {

    private final UserService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors error) {
        UserDto request = (UserDto) target;
        UserResponse user = service.findByEmail(request.getEmail());
        if (StringUtils.nonNull(user))
            throw new CustomMessagePresentException("This email " + ALREADY_EXIST);
    }
}
