package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.repository.AccountRepository;
import com.fleemer.webmvc.service.AccountService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl extends AbstractService<Account, Long, AccountRepository> implements AccountService {
    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    protected AccountRepository getRepository() {
        return repository;
    }

    @Override
    public Optional<Account> findByNameAndPerson(String name, Person person) {
        return repository.findByNameAndPerson(name, person);
    }
}
