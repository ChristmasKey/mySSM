package com.stone;

import com.stone.dao.OrderDao;
import com.stone.factory.OrderDaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppForInstanceOrder {

    public static void main(String[] args) {
        // 通过静态工厂创建对象
        //OrderDao orderDao = OrderDaoFactory.getOrderDaoInstance();
        //orderDao.save();

        // 通过Spring调用静态工厂创建对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderDao orderDao = (OrderDao) context.getBean("orderDao");
        orderDao.save();
    }
}
