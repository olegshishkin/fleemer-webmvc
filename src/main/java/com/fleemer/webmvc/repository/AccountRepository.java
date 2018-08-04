package com.fleemer.webmvc.repository;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNameAndPerson(String name, Person person);
}
