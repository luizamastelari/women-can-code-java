package com.womencancode.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/helloworld/{name}")
    public String helloWorld(@PathVariable String name) {
        return "Hello " + name;
    }
    
    @GetMapping("/OiAmigo")
    public String helloWorld2() {
        return "OiAmigo";
    }
    
    @GetMapping ("/OiAmigo/{cor}")
    public String helloWorld2 (@PathVariable String cor){
        return "Oi Amigo " + cor; 
    }
}
