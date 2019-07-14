package com.womencancode.rbac.mock;

import com.womencancode.rbac.model.User;

import java.util.Arrays;
import java.util.List;

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

    public static List<User> getUserListMock() {
        return Arrays.asList(getUserMock(), getUserMock(), getUserMock());
    }

    public static List<User> getUserListMock(String id) {
        int i = 1;
        return Arrays.asList(getUserMock().withId(id + i++), getUserMock().withId(id + i++), getUserMock().withId(id + i++));
    }
}
