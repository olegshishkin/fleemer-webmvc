package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.repository.CategoryRepository;
import com.fleemer.webmvc.service.CategoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl extends AbstractService<Category, Long, CategoryRepository> implements CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CategoryRepository getRepository() {
        return repository;
    }

    @Override
    public Optional<Category> findByNameAndPerson(String name, Person person) {
        return repository.findByNameAndPerson(name, person);
    }
}
