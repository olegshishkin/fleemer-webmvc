package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.repository.OperationRepository;
import javax.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OperationService extends AbstractService<Operation, Long, OperationRepository> {
    private final OperationRepository repository;

    @Autowired
    public OperationService(@NonNull OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }
}
