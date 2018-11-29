package com.karli.commons.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/")
    public String baseURL() {
        return "Hello world";
    }

    @GetMapping(
            value = "/test",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<String> isApiOnline() {
        List<String> test = new ArrayList<>();
        test.add("Hello");
        test.add("world");
        return test;
    }
}
