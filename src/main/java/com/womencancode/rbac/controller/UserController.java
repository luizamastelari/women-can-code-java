package com.womencancode.rbac.controller;

import com.womencancode.rbac.model.User;
import com.womencancode.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(service.insertUser(user));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<User>> insertUsers(@RequestBody List<User> users) throws Exception {
        return ResponseEntity.ok(service.insertUser(users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String id) throws Exception {
        user.setId(id);
        return ResponseEntity.ok(service.updateUser(user));
    }

    @GetMapping
    public ResponseEntity<Stream<User>> getAll(@SortDefault.SortDefaults(
            {@SortDefault(sort = "name", direction = Sort.Direction.ASC)}) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable).get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(service.findById(id));
    }
}
