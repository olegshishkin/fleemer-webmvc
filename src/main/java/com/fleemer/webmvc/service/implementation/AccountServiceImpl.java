package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.repository.AccountRepository;
import com.fleemer.webmvc.service.AccountService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    public Optional<Account> findByNameAndPerson(@NotNull @Size(min = 1, max = 255) String name, Person person) {
        return repository.findByNameAndPerson(name, person);
    }

    @Override
    public List<Account> findAllByPerson(@NonNull Person person) {
        return repository.findAllByPerson(person);
    }
}
