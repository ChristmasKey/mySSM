package com.stone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer id) {
        System.out.println("id ==> " + id);
        return "Hello, SpringBoot!";
    }
}
