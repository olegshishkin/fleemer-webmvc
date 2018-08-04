package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Person;
import java.util.Optional;

public interface PersonService extends BaseService<Person, Long> {
    Optional<Person> findByEmail(String email);
}
