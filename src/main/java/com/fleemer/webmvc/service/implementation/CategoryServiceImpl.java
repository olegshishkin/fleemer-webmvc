package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.repository.CategoryRepository;
import com.fleemer.webmvc.service.CategoryService;
import java.util.List;
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
    protected CategoryRepository getOperationRepository() {
        return repository;
    }

    @Override
    public Optional<Category> findByNameAndPerson(String name, Person person) {
        return repository.findByNameAndPerson(name, person);
    }

    @Override
    public List<Category> findAll(Person person) {
        return repository.findAllByPersonOrderByName(person);
    }

    @Override
    public List<Category> findAllByTypeAndPerson(CategoryType type, Person person) {
        return repository.findAllByTypeAndPerson(type, person);
    }
}
