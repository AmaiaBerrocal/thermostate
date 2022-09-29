package com.thermostate.infrastructure.controllers;

import com.thermostate.infrastructure.properties.PropertiesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    PropertiesLoader properties;
    public HelloWorldController(PropertiesLoader properties) {
        this.properties = properties;
    }

    @GetMapping("helloworld")
    public String helloWorld() {
        return properties.getUrl();
    }
}
