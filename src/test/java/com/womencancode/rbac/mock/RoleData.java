package com.womencancode.rbac.mock;

import com.womencancode.rbac.model.Role;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RoleData {

    public static final String ROLE_1 = "role1";

    public static Role getRoleMock() {
        return Role.builder()
                .name(ROLE_1)
                .build();
    }

    public static Role getRoleMock(String id) {
        return Role.builder()
                .id(id)
                .name(ROLE_1)
                .build();
    }

    public static List<Role> getRolesMock() {
        return Arrays.asList(getRoleMock(UUID.randomUUID().toString()),
                getRoleMock(UUID.randomUUID().toString()),
                getRoleMock(UUID.randomUUID().toString()));
    }
}
