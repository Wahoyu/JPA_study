package com.example.service.impl;

import com.example.entity.Account;
import com.example.repo.AccountRepository;
import com.example.service.AccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountRepository repository;

    @Override
    public void createAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(password));

        repository.save(account);
    }

}
