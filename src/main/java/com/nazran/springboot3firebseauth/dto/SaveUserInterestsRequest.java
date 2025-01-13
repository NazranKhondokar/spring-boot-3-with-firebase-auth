package com.nazran.springboot3firebseauth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for saving user interests.
 */
@Getter
@Setter
public class SaveUserInterestsRequest {

    @NotEmpty(message = "Interests list cannot be empty.")
    private List<UserInterestDTO> interests;

    /**
     * Inner DTO representing a user interest selection.
     */
    @Getter
    @Setter
    public static class UserInterestDTO {

        @NotNull(message = "Group ID cannot be null.")
        private Integer groupId;

        @NotNull(message = "Interest ID cannot be null.")
        private Integer interestId;
    }
}
