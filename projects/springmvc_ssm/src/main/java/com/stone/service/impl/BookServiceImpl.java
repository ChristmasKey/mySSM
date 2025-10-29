package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.domain.Book;
import com.stone.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean addBook(Book book) {
        bookDao.save(book);
        return true;
    }

    @Override
    public boolean deleteBook(Integer id) {
        bookDao.delete(id);
        return true;
    }

    @Override
    public boolean updateBook(Book book) {
        bookDao.update(book);
        return true;
    }

    @Override
    public Book getBookById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }
}
