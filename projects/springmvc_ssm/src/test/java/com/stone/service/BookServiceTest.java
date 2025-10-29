package com.stone.service;

import com.stone.config.SpringConfig;
import com.stone.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testGetById() {
        Book book = bookService.getBookById(1);
        System.out.println(book);
    }

    @Test
    public void testGetAll() {
        bookService.getAllBooks().forEach(System.out::println);
    }

}
