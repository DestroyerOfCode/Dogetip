package com.doge.tip.controller.implementation.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class TestController {

    @GetMapping
    public int test(){
        int i = 0;
        return i;
    }
}
