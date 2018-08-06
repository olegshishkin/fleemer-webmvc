package com.fleemer.webmvc.repository;

import java.util.List;
import java.util.Optional;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByPersonOrderByName(Person person);

    Optional<Category> findByNameAndPerson(String name, Person person);
}
