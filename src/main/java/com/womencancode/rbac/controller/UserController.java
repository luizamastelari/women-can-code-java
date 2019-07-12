package com.womencancode.rbac.controller;

import com.womencancode.rbac.model.User;
import com.womencancode.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(service.insertUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String id) throws Exception {
        user.setId(id);
        return ResponseEntity.ok(service.updateUser(user));
    }
}
