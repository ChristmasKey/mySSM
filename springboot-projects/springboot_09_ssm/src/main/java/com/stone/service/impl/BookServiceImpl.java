package com.stone.service.impl;

import com.stone.controller.Code;
import com.stone.dao.BookDao;
import com.stone.domain.Book;
import com.stone.exception.BusinessException;
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
        if (id == 1) {
            throw new BusinessException(Code.BUSINESS_ERR, "请不要使用你的技术挑战我的耐性！");
        }
        return bookDao.getById(id);
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return bookDao.delete(id) > 0;
    }

    @Override
    public boolean save(Book book) {
        return bookDao.save(book) > 0;
    }
}
