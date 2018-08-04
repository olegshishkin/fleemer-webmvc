package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.repository.PersonRepository;
import com.fleemer.webmvc.service.PersonService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonServiceImpl extends AbstractService<Person, Long, PersonRepository> implements PersonService {
    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PersonRepository getRepository() {
        return repository;
    }

    public Optional<Person> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
