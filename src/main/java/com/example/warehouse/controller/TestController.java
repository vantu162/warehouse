package com.example.warehouse.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/warehouse/content")
    public String content_0(){
        return "content 0";
    }

    @PostMapping("/warehouse/content_1")
    public String content_1(){
        return "content 1";
    }

    @PostMapping("/warehouse/content_2")
    public String content_2(){
        return "content 2";
    }
}



