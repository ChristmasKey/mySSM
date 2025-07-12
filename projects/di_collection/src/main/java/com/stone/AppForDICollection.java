package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForDICollection {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookDao bookDao = context.getBean("bookDao", BookDao.class);

        bookDao.save();
    }
}
