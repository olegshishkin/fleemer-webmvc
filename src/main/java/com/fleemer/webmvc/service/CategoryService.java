package com.fleemer.webmvc.service;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.repository.CategoryRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category, Long, CategoryRepository> {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(@NonNull CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CategoryRepository getRepository() {
        return repository;
    }
}
