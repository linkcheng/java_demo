package com.example.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AdminController {
    @Autowired
    TestSingleton testSingleton;

    @GetMapping("/haha")
    public String getString(HttpRequest request){
        return request.getURI().toString();
    }
}
