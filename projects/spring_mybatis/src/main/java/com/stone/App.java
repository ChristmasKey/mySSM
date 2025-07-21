package com.stone;

import com.stone.dao.AccountDao;
import com.stone.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class App {
    public static void main(String[] args) throws IOException {
        // 1. 创建 SqlSessionFactoryBuilder 对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 2. 加载配置文件 SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 3. 创建 SqlSessionFactory 对象
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        // 4. 创建 SqlSession 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 5. 执行操作
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        Account account = accountDao.findById(1);
        System.out.println(account);
        // 6. 释放资源
        sqlSession.close();
    }
}
