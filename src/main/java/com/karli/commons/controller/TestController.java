package com.karli.commons.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/test")
    public List<String> isApiOnline() {
        List<String> test = new ArrayList<>();
        test.add("Tere");
        test.add("Test");
        return test;
    }
}
