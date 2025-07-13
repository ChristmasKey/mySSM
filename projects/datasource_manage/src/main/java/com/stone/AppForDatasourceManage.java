package com.stone;

import com.stone.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class AppForDatasourceManage {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //DataSource dataSource = context.getBean("dataSource", DataSource.class);
        //System.out.println(dataSource);

        //DataSource c3poDataSource = context.getBean("c3p0DataSource", DataSource.class);
        //System.out.println(c3poDataSource);

        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();
    }
}
