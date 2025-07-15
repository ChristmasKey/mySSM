package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AppForContainer {
    public static void main(String[] args) {
        //加载配置文件
        //1.加载类路径下的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.加载文件系统下的配置文件
        //ApplicationContext context = new FileSystemXmlApplicationContext("D:\\StoneSpace\\mySSM\\projects\\container\\src\\main\\resources\\applicationContext.xml");
        //3.加载多个配置文件
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml", "bean2.xml");
        //ApplicationContext context = new FileSystemXmlApplicationContext("...", "...");

        //获取bean
        //1.使用Bean名称获取
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        //2.使用Bean名称获取并指定类型
        //BookDao bookDao = context.getBean("bookDao", BookDao.class);
        //3.使用Bean类型获取（需保证容器中对应类型的Bean是唯一的）
        //BookDao bookDao = context.getBean(BookDao.class);

        bookDao.save();
    }
}
