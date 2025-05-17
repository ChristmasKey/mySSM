package com.stone;

import com.stone.dao.UserDao;
import com.stone.factory.UserDaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForInstanceUser {

    public static void main(String[] args) {
        // 创建实例工厂对象
        //UserDaoFactory userDaoFactory = new UserDaoFactory();
        // 通过实力工厂对象创建Dao对象实例
        //UserDao userDao = userDaoFactory.getUserDao();
        //userDao.save();

        // 通过Spring创建实例工厂对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) context.getBean("userDao2");
        userDao.save();

        UserDao userDao2 = (UserDao) context.getBean("userDao2");
        System.out.println(userDao);
        System.out.println(userDao2);

    }
}
