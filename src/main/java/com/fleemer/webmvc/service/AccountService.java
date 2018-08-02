package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.repository.AccountRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends AbstractService<Account, Long, AccountRepository> {
    private final AccountRepository repository;

    @Autowired
    public AccountService(@NonNull AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    protected AccountRepository getRepository() {
        return repository;
    }
}
