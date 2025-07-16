package com.stone;

import com.stone.config.SpringConfig;
import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        BookDao bookDao1 = context.getBean(BookDao.class);
        BookDao bookDao2 = context.getBean(BookDao.class);
        System.out.println(bookDao1);
        System.out.println(bookDao2);

        context.close();
    }
}
