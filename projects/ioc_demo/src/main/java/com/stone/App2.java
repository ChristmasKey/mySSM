package com.stone;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {

    public static void main(String[] args) {
        // 获取IoC容器
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 从容器中获取Bean对象
        //BookDao bookDao = (BookDao) context.getBean("bookDao");
        //bookDao.save();
        BookService bookService = (BookService) context.getBean("bookService");
        bookService.save();
    }
}
