package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OperationService extends BaseService<Operation, Long> {
    List<Operation> findAll(Person person);

    Page<Operation> findAll(Person person, Pageable pageable);
}
