package com.stone.controller;

import com.stone.domain.Book;
import com.stone.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Result addBook(@RequestBody Book book) {
        boolean flag = bookService.addBook(book);
        return new Result(flag, flag ? Code.SAVE_OK : Code.SAVE_ERR);
    }

    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable("id") Integer id) {
        boolean flag = bookService.deleteBook(id);
        return new Result(flag, flag ? Code.DELETE_OK : Code.DELETE_ERR);
    }

    @PutMapping
    public Result updateBook(@RequestBody Book book) {
        boolean flag = bookService.updateBook(book);
        return new Result(flag, flag ? Code.UPDATE_OK : Code.UPDATE_ERR);
    }

    @GetMapping("/{id}")
    public Result getBookById(@PathVariable("id") Integer id) {
        // int i = 1 / 0; // 模拟异常
        Book book = bookService.getBookById(id);
        if (book != null) {
            return new Result(book, "数据查询成功", Code.GET_OK);
        } else {
            return new Result(null, "数据查询失败，请重试！", Code.GET_ERR);
        }
    }

    @GetMapping
    public Result getAllBooks() {
        List<Book> bookList = bookService.getAllBooks();
        if (bookList != null) {
            return new Result(bookList, "数据查询成功", Code.GET_OK);
        } else {
            return new Result(null, "数据查询失败，请重试！", Code.GET_ERR);
        }
    }
}
