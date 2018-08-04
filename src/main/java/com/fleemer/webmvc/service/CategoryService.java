package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import java.util.Optional;

public interface CategoryService extends BaseService<Category, Long> {
    Optional<Category> findByNameAndPerson(String name, Person person);
}
