package com.stone.service;

import com.stone.domain.Book;

import java.util.List;

public interface BookService {

    public int save(Book book);

    public int delete(Integer id);

    public int update(Book book);

    public Book getById(Integer id);

    public List<Book> getAll();
}
