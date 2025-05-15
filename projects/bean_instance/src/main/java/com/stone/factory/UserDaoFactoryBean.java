package com.stone.factory;

import com.stone.dao.UserDao;
import com.stone.dao.impl.UserDaoImpl;
import org.springframework.beans.factory.FactoryBean;

public class UserDaoFactoryBean implements FactoryBean<UserDao> {

    // 代替原始实例工厂中创建对象的方法
    public UserDao getObject() throws Exception {
        return new UserDaoImpl();
    }

    // 获取Bean对象类型
    public Class<?> getObjectType() {
        return UserDao.class;
    }

    // 配置Bean对象是否为单例
    public boolean isSingleton() {
        return false;
    }
}
