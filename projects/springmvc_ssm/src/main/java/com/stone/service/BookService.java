package com.stone.service;

import com.stone.domain.Book;

import java.util.List;

public interface BookService {

    /**
     * 添加图书
     * @param book 图书信息
     * @return 保存结果
     */
    public boolean addBook(Book book);

    /**
     * 删除图书
     * @param id 图书id
     * @return 删除结果
     */
    public boolean deleteBook(Integer id);

    /**
     * 修改图书
     * @param book 图书信息
     * @return 修改结果
     */
    public boolean updateBook(Book book);

    /**
     * 根据id查询图书
     * @param id 图书id
     * @return 图书信息
     */
    public Book getBookById(Integer id);

    /**
     * 查询所有图书
     * @return 图书列表
     */
    public List<Book> getAllBooks();
}
