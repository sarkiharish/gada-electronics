package com.hari.electronic.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HomeController {
    @GetMapping
    private String testing() {
        return "Welcome to gada electronics";
    }
}
