package com.stone.controller;

import com.stone.domain.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public String save(@RequestBody Book book) {
        System.out.println("book save ==> " + book);
        return "{'module':'book save success'}";
    }

    @GetMapping
    public List<Book> getAll() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setType("TYPE");
        book1.setName("java");
        book1.setDescription("good book");
        Book book2 = new Book();
        book2.setId(2);
        book2.setType("TYPE");
        book2.setName("python");
        book2.setDescription("nice book");
        List<Book> books = new ArrayList<Book>();
        books.add(book1);
        books.add(book2);
        return books;
    }
}
