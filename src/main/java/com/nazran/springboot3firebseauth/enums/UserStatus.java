package com.nazran.springboot3firebseauth.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    Inactive(0),
    Active(1);


    private final Integer label;

    UserStatus(Integer label) {
        this.label = label;
    }
}
