package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.repository.CategoryRepository;
import com.fleemer.webmvc.service.CategoryService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
