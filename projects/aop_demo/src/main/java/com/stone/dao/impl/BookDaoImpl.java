package com.stone.dao.impl;

import com.stone.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

    public void save() {
        // 记录程序执行开始时间
        Long startTime = System.currentTimeMillis();
        // 业务执行
        for (int i = 0; i < 10000; i++) {
            System.out.println("book dao save...");
        }
        // 记录程序执行结束时间
        Long endTime = System.currentTimeMillis();
        // 计算程序执行时间
        System.out.println("程序执行时间：" + (endTime - startTime) + "ms");
    }

    public void select() {
        System.out.println("book dao select...");
    }

    public void delete() {
        System.out.println("book dao delete...");
    }

    public void update() {
        System.out.println("book dao update...");
    }
}
