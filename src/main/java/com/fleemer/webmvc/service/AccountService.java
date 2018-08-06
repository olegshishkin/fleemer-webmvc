package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService extends BaseService<Account, Long> {
    Optional<Account> findByNameAndPerson(String name, Person person);

    List<Account> findAll(Person person);

    BigDecimal getTotalBalance(Person person);
}
