package com.stone.service.impl;

import com.stone.controller.Code;
import com.stone.dao.BookDao;
import com.stone.domain.Book;
import com.stone.exception.BusinessException;
import com.stone.exception.SystemException;
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
        // 模拟抛出业务异常
        if (id == 1) {
            throw new BusinessException("请规范操作！", Code.BUSINESS_ERR);
        }
        // 将可能出现的异常进行包装，转换成自定义异常
        // try {
        //     int i = 1 / 0;
        // } catch (Exception e) {
        //     throw new SystemException("服务器访问超时，请重试！", e, Code.SYSTEM_TIMEOUT_ERR);
        // }
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }
}
