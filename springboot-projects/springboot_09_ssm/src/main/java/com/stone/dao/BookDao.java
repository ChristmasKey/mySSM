package com.stone.dao;

import com.stone.domain.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookDao {

    @Insert("insert into tbl_book(name, type, description) values(#{name}, #{type}, #{description})")
    public int save(Book book);

    @Update("update tbl_book set name=#{name}, type=#{type}, description=#{description} where id=#{id}")
    public int update(Book book);

    @Delete("delete from tbl_book where id=#{id}")
    public int delete(Integer id);

    @Select("select * from tbl_book where id=#{id}")
    public Book getById(Integer id);

    @Select("select * from tbl_book")
    public List<Book> getAll();
}
