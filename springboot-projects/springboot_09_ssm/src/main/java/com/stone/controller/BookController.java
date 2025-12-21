package com.stone.controller;

import com.stone.domain.Book;
import com.stone.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Integer id) {
        return bookService.getById(id);
    }

    @PostMapping
    public int save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping
    public int update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return bookService.delete(id);
    }
}
