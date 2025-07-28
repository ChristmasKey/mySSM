package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    public void update() {
        System.out.println("book dao update...");
    }

    public void save() {
        System.out.println(System.currentTimeMillis());
        System.out.println("book dao save...");
    }
}
