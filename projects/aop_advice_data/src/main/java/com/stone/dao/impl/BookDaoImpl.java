package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    @Override
    public String findName(int id, String name) {
        System.out.println("id: " + id);
        return name;
    }
}
