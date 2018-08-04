package com.fleemer.webmvc.repository;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndPerson(String name, Person person);
}
