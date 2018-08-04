package com.fleemer.webmvc.repository;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Iterable<Operation> findAllByAccount_PersonOrCategory_Person(Person accountPerson, Person categoryPerson);
}
