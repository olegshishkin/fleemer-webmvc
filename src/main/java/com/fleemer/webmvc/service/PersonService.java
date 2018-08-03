package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Person;
import java.util.Optional;
import javax.validation.constraints.Email;
import org.springframework.lang.NonNull;

public interface PersonService extends BaseService<Person, Long> {
    Optional<Person> findByEmail(@NonNull @Email String email);
}
