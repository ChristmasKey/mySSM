package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForLifeCycle {

    public static void main(String[] args) {
        // 转换IoC容器的接收类型
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        bookDao.save();
        // 手动关闭IoC容器
        context.close();
    }
}
