package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import java.util.List;

public interface OperationService extends BaseService<Operation, Long> {
    List<Operation> findAll(Person person);

    List<Operation> findAll(Person person, int page, int size);
}
