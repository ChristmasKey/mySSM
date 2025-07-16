package com.stone;

import com.stone.dao.BookDao;
import com.stone.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();

        // 由于没有在注解中设置Bean的名称，所以这里应该通过类型类获取Bean
        BookService bookService = context.getBean(BookService.class);
        System.out.println(bookService);
    }
}
