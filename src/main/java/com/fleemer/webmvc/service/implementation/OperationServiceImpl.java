package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.repository.OperationRepository;
import com.fleemer.webmvc.service.OperationService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
