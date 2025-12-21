package com.stone;

import com.stone.dao.BookDao;
import com.stone.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Springboot08MybatisApplicationTests {

    @Resource
    private BookDao bookDao;

    @Test
    void contextLoads() {
        Book book = bookDao.getById(1);
        System.out.println(book);
    }

}
