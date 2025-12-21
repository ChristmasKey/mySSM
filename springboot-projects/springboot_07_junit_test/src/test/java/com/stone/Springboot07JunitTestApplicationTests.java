package com.stone;

import com.stone.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Springboot07JunitTestApplicationTests {

    @Resource
    private BookService bookService;

    @Test
    void contextLoads() {
        bookService.save();
    }

}
