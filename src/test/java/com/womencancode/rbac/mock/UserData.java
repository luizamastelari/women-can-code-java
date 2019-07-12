package com.womencancode.rbac.mock;

import com.womencancode.rbac.model.User;

public class UserData {

    public static final String NAME = "test";
    public static final String LAST_NAME = "mvc";
    public static final String EMAIL = "test@teste.com";
    public static final String USERNAME = "me";

    public static User getUserMock() {
        return User.builder()
                .name(NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .username(USERNAME)
                .build();
    }
}
