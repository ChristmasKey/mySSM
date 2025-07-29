package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    public int select() {
        System.out.println("book dao select...");
        // System.out.println(10 / 0);
        return 100;
    }

    public void update() {
        System.out.println("book dao update...");
    }
}
