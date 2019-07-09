package com.womencancode.rbac.repository;

import com.womencancode.rbac.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByNameIgnoreCase(String name);
}
