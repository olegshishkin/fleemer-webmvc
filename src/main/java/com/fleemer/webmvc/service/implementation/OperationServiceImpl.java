package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.repository.OperationRepository;
import com.fleemer.webmvc.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationServiceImpl extends AbstractService<Operation, Long, OperationRepository> implements OperationService {
    private final OperationRepository repository;

    @Autowired
    public OperationServiceImpl(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }

    @Override
    public Iterable<Operation> findAll(Person person) {
        return repository.findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person);
    }
}
