package com.fleemer.webmvc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.repository.CategoryRepository;
import com.fleemer.webmvc.service.exception.ServiceException;
import com.fleemer.webmvc.service.implementation.CategoryServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    private long id = 123L;

    @InjectMocks
    private CategoryServiceImpl service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private Category category;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<Category> page;

    @Mock
    private Sort sort;

    @Test
    public void count() {
        when(repository.count()).thenReturn(id);
        assertEquals(id, service.count());
        verify(repository, times(1)).count();
    }

    @Test
    public void existsById() {
        when(repository.existsById(id)).thenReturn(true);
        assertTrue(service.existsById(id));
        verify(repository, times(1)).existsById(id);
    }

    @Test
    public void getOne() {
        when(repository.getOne(id)).thenReturn(category);
        assertEquals(category, service.getOne(id));
        verify(repository, times(1)).getOne(id);
    }

    @Test
    public void findById() {
        Optional<Category> optional = Optional.of(category);
        when(repository.findById(id)).thenReturn(optional);
        assertEquals(optional, service.findById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void findAllById() {
        List<Long> ids = Collections.emptyList();
        List<Category> categories = Collections.emptyList();
        when(repository.findAllById(ids)).thenReturn(categories);
        assertEquals(categories, service.findAllById(ids));
        verify(repository, times(1)).findAllById(ids);
    }

    @Test
    public void findAll_pageable() {
        when(repository.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void findAll() {
        List<Category> categories = Collections.emptyList();
        when(repository.findAll()).thenReturn(categories);
        assertEquals(categories, service.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findAll_sort() {
        List<Category> categories = Collections.emptyList();
        when(repository.findAll(sort)).thenReturn(categories);
        assertEquals(categories, service.findAll(sort));
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    public void save() throws ServiceException {
        when(repository.save(category)).thenReturn(category);
        assertEquals(category, service.save(category));
        verify(repository, times(1)).save(category);
    }

    @Test
    public void saveAll() {
        List<Category> categories = Collections.emptyList();
        when(repository.saveAll(categories)).thenReturn(categories);
        assertEquals(categories, service.saveAll(categories));
        verify(repository, times(1)).saveAll(categories);
    }

    @Test
    public void deleteById() {
        doNothing().when(repository).deleteById(id);
        service.deleteById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void delete() {
        doNothing().when(repository).delete(category);
        service.delete(category);
        verify(repository, times(1)).delete(category);
    }

    @Test
    public void deleteAll_iterable() {
        List<Category> categories = Collections.emptyList();
        doNothing().when(repository).deleteAll(categories);
        service.deleteAll(categories);
        verify(repository, times(1)).deleteAll(categories);
    }

    @Test
    public void deleteAll() {
        doNothing().when(repository).deleteAll();
        service.deleteAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    public void deleteInBatch() {
        List<Category> categories = Collections.emptyList();
        doNothing().when(repository).deleteInBatch(categories);
        service.deleteInBatch(categories);
        verify(repository, times(1)).deleteInBatch(categories);
    }

    @Test
    public void deleteAllInBatch() {
        doNothing().when(repository).deleteAllInBatch();
        service.deleteAllInBatch();
        verify(repository, times(1)).deleteAllInBatch();
    }
}