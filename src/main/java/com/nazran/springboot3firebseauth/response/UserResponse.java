package com.nazran.springboot3firebseauth.response;

import com.nazran.springboot3firebseauth.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String normalizedEmail;
    private Boolean isEmailVerified;
    private String status;
    private String firebaseUserId;
    public static UserResponse from(User user) {
        if (user == null) {
            return null; // or return a default UserResponse object
        }
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setNormalizedEmail(user.getNormalizedEmail());
        response.setIsEmailVerified(user.getIsEmailVerified());
        response.setStatus(String.valueOf(user.getUserStatus()));
        response.setFirebaseUserId(user.getFirebaseUserId());
        return response;
    }
}
