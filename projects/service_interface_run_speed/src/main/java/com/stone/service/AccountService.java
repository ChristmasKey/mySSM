package com.stone.service;

import com.stone.domain.Account;

import java.util.List;

public interface AccountService {

    void save(Account account);

    void update(Account account);

    void delete(int id);

    Account findById(int id);

    List<Account> findAll();
}
