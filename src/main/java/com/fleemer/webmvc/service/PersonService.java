package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.repository.PersonRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonService extends AbstractService<Person, Long, PersonRepository> {
    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PersonRepository getRepository() {
        return repository;
    }
}
