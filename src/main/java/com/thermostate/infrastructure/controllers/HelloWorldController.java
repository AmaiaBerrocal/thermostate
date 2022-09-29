package com.thermostate.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("helloworld")
    public String helloWorld() {
        return "Hello world!";
    }
}
