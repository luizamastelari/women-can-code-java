package com.womencancode.rbac.model;

import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Wither;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@Data
@Builder
@Wither
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

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @DBRef
    private Role role;
}
