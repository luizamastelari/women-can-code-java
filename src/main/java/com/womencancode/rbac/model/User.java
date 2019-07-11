package com.womencancode.rbac.model;

import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@Builder
public class User {

    @Id
    private String id;
    private String name;
    private String lastName;

    @NonNull
    private String username;

    @NonNull
    private String email;

    private LocalDate birthDate;

    @DBRef(db = "role", lazy = false)
    private Role role;
}
