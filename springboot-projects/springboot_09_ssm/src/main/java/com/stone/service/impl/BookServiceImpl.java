package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.domain.Book;
import com.stone.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookDao bookDao;

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public int update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public int delete(Integer id) {
        return bookDao.delete(id);
    }

    @Override
    public int save(Book book) {
        return bookDao.save(book);
    }
}
