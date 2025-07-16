package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository
// @Scope("prototype")
public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book dao save...");
    }

    @PostConstruct
    public void customInit() {
        System.out.println("book dao init...");
    }

    @PreDestroy
    public void customDestroy() {
        System.out.println("book dao destroy...");
    }
}
