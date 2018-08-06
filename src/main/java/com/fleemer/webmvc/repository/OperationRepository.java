package com.fleemer.webmvc.repository;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(Person inPerson, Person outPerson,
                                                                               Person categoryPerson);

    List<Operation> findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(Person inPerson, Person outPerson,
                                                                               Person categoryPerson, Pageable pageable);
}
