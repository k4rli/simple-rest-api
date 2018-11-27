package com.mountbirch.commons.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kaur Laanemäe on 23/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@RestController
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity isApiOnline(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
