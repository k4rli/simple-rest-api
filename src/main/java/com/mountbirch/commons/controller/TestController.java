package com.mountbirch.commons.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaur Laanemäe on 23/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@RestController
public class TestController {

    @GetMapping("/authenticated/test")
    public List<String> isApiOnline(){
        List<String> test = new ArrayList<>();
        test.add("Tere");
        test.add("Test");
        return test;
    }
}
