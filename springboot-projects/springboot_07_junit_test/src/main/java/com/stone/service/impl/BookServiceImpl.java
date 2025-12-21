package com.stone.service.impl;

import com.stone.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public void save() {
        System.out.println("book service is running...");
    }

}
