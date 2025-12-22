package com.stone.service;

import com.stone.domain.Book;

import java.util.List;

public interface BookService {

    public boolean save(Book book);

    public boolean delete(Integer id);

    public boolean update(Book book);

    public Book getById(Integer id);

    public List<Book> getAll();
}
