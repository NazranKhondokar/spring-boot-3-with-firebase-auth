package com.nazran.springboot3firebseauth.dto;

import com.nazran.springboot3firebseauth.entity.User;
import com.nazran.springboot3firebseauth.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private String normalizedEmail;

    public User to() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNormalizedEmail(normalizedEmail);
        user.setUserStatus(UserStatus.Active);
        user.setFirebaseUserId("v4xpr8hLrLR3W5VUTN2zZ3XXKrF3");
        return user;
    }

    public User update(User user) {
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNormalizedEmail(normalizedEmail);
        user.setUserStatus(UserStatus.Active);
        user.setFirebaseUserId("v4xpr8hLrLR3W5VUTN2zZ3XXKrF3");
        return user;
    }
}