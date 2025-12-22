package com.stone.service;

import com.stone.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class BookServiceTest {

    @Resource
    private BookService bookService;

    @Test
    public void testGetById() {
        Book book = bookService.getById(2);
        System.out.println(book);
    }

    @Test
    public void testGetAll() {
        List<Book> bookList = bookService.getAll();
        System.out.println(bookList);
    }
}
