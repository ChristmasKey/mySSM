package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForLifeCycle {

    public static void main(String[] args) {
        // 转换IoC容器的接收类型
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        bookDao.save();
        // 注册关闭钩子
        context.registerShutdownHook(); // 在创建context后，随时都可以调用
        // 手动关闭IoC容器
        //context.close(); // 只能在代码执行的最后调用
    }
}
