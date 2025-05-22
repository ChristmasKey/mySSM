package com.stone.service.impl;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BookServiceImpl implements BookService, InitializingBean, DisposableBean {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void save() {
        bookDao.save();
        System.out.println("book service save ...");
    }

    public void destroy() throws Exception {
        System.out.println("service destroy ...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("service init ...");
    }
}
