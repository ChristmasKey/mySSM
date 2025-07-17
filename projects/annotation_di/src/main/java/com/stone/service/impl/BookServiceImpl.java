package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    @Qualifier("bookDao2")
    private BookDao bookDao;

    // 由于@Autowired注解是用反射机制中的暴力反射直接给属性赋值，所以不再需要setter方法
    // public void setBookDao(BookDao bookDao) {
    //     this.bookDao = bookDao;
    // }

    public void save() {
        System.out.println("service save...");
        bookDao.save();
    }
}
