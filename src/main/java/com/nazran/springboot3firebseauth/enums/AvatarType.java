package com.nazran.springboot3firebseauth.enums;

import lombok.Getter;

@Getter
public enum AvatarType {

    Standard(0),
    RedEyes(1),
    Custom(2);

    private final Integer label;

    AvatarType(Integer label) {
        this.label = label;
    }
}
