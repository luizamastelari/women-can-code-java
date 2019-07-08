package com.womencancode.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World";
    }

}
