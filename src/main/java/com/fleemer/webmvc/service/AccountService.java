package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.lang.NonNull;

public interface AccountService extends BaseService<Account, Long> {
    Optional<Account> findByNameAndPerson(@NotNull @Size(min = 1, max = 255) String name, Person person);
    List<Account> findAllByPerson(@NonNull Person person);
}
