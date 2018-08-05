package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;

public interface OperationService extends BaseService<Operation, Long> {
    Iterable<Operation> findAll(Person inPerson);
}
