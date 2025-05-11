package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;

public class BookServiceImpl implements BookService {

    // 移除new方式创建的对象
    private BookDao bookDao;

    public void save() {
        System.out.println("book service save...");
        bookDao.save();
    }

    // 提供dao对象对应的setter方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
