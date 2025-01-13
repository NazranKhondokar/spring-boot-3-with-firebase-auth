package com.nazran.springboot3firebseauth.enums;

import lombok.Getter;

@Getter
public enum ApplicationTheme {

    System(0),
    Dark(1),
    Light(2);

    private final Integer label;

    ApplicationTheme(Integer label) {
        this.label = label;
    }
}
