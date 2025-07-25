package com.stone.dao;

import com.stone.domain.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao {

    @Insert("insert into t_account(name,money) values(#{name},#{money})")
    void save(Account account);

    @Update("update t_account set name=#{name},money=#{money} where id=#{id}")
    void update(Account account);

    @Delete("delete from t_account where id=#{id}")
    void delete(Integer id);

    @Select("select * from t_account")
    List<Account> findAll();

    @Select("select * from t_account where id=#{id}")
    Account findById(Integer id);
}
