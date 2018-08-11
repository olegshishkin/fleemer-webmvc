package com.fleemer.webmvc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.repository.OperationRepository;
import com.fleemer.webmvc.service.exception.ServiceException;
import com.fleemer.webmvc.service.implementation.OperationServiceImpl;
import java.math.BigDecimal;
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
public class OperationServiceImplTest {
    private long id = 123L;

    @InjectMocks
    private OperationServiceImpl service;

    @Mock
    private OperationRepository repository;

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Operation operation;

    @Mock
    private Pageable pageable;

    @Mock
    private Person person;

    @Mock
    private Page<Operation> page;

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
        when(repository.getOne(id)).thenReturn(operation);
        assertEquals(operation, service.getOne(id));
        verify(repository, times(1)).getOne(id);
    }

    @Test
    public void findById() {
        Optional<Operation> optional = Optional.of(operation);
        when(repository.findById(id)).thenReturn(optional);
        assertEquals(optional, service.findById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void findAllById() {
        List<Long> ids = Collections.emptyList();
        List<Operation> operations = Collections.emptyList();
        when(repository.findAllById(ids)).thenReturn(operations);
        assertEquals(operations, service.findAllById(ids));
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
        List<Operation> operations = Collections.emptyList();
        when(repository.findAll()).thenReturn(operations);
        assertEquals(operations, service.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findAll_sort() {
        List<Operation> operations = Collections.emptyList();
        when(repository.findAll(sort)).thenReturn(operations);
        assertEquals(operations, service.findAll(sort));
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    public void saveAll() {
        List<Operation> operations = Collections.emptyList();
        when(repository.saveAll(operations)).thenReturn(operations);
        assertEquals(operations, service.saveAll(operations));
        verify(repository, times(1)).saveAll(operations);
    }

    @Test
    public void deleteById() {
        doNothing().when(repository).deleteById(id);
        service.deleteById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void delete() {
        doNothing().when(repository).delete(operation);
        service.delete(operation);
        verify(repository, times(1)).delete(operation);
    }

    @Test
    public void deleteAll_iterable() {
        List<Operation> operations = Collections.emptyList();
        doNothing().when(repository).deleteAll(operations);
        service.deleteAll(operations);
        verify(repository, times(1)).deleteAll(operations);
    }

    @Test
    public void deleteAll() {
        doNothing().when(repository).deleteAll();
        service.deleteAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    public void deleteInBatch() {
        List<Operation> operations = Collections.emptyList();
        doNothing().when(repository).deleteInBatch(operations);
        service.deleteInBatch(operations);
        verify(repository, times(1)).deleteInBatch(operations);
    }

    @Test
    public void deleteAllInBatch() {
        doNothing().when(repository).deleteAllInBatch();
        service.deleteAllInBatch();
        verify(repository, times(1)).deleteAllInBatch();
    }

    @Test
    public void findAll_person() {
        List<Operation> expected = Collections.emptyList();
        when(repository.findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person)).thenReturn(expected);
        assertEquals(expected, service.findAll(person));
        verify(repository, times(1)).findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person);
    }

    @Test
    public void findAll_personAndPageable() {
        when(repository.findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person, pageable)).thenReturn(page);
        assertEquals(page, service.findAll(person, pageable));
        verify(repository, times(1)).findAllByInAccountPersonOrOutAccountPersonOrCategoryPerson(person, person, person, pageable);
    }

    @Test
    public void save_outcomeNull() throws ServiceException {
        Category category = new Category();
        category.setType(CategoryType.INCOME);

        Account in = new Account();
        in.setBalance(BigDecimal.valueOf(10.01));

        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setCategory(category);
        operation.setInAccount(in);
        when(accountService.save(in)).thenReturn(in);
        when(categoryService.save(category)).thenReturn(category);
        when(repository.save(operation)).thenReturn(operation);
        assertEquals(operation, service.save(operation));
        assertEquals(13.46, in.getBalance().doubleValue(), 0.0);
        verify(accountService, times(1)).save(in);
        verify(categoryService, times(1)).save(category);
        verify(repository, times(1)).save(operation);
    }

    @Test
    public void save_incomeNull() throws ServiceException {
        Category category = new Category();
        category.setType(CategoryType.OUTCOME);

        Account out = new Account();
        out.setBalance(BigDecimal.valueOf(10.01));

        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setCategory(category);
        operation.setOutAccount(out);
        when(accountService.save(out)).thenReturn(out);
        when(categoryService.save(category)).thenReturn(category);
        when(repository.save(operation)).thenReturn(operation);
        assertEquals(operation, service.save(operation));
        assertEquals(6.56, out.getBalance().doubleValue(), 0.0);
        verify(accountService, times(1)).save(out);
        verify(categoryService, times(1)).save(category);
        verify(repository, times(1)).save(operation);
    }

    @Test
    public void save_categoryNull() throws ServiceException {
        Account in = new Account();
        in.setBalance(BigDecimal.valueOf(6.09));

        Account out = new Account();
        out.setBalance(BigDecimal.valueOf(10.01));

        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setInAccount(in);
        operation.setOutAccount(out);
        when(accountService.save(in)).thenReturn(in);
        when(accountService.save(out)).thenReturn(out);
        when(repository.save(operation)).thenReturn(operation);
        assertEquals(operation, service.save(operation));
        assertEquals(9.54, in.getBalance().doubleValue(), 0.0);
        assertEquals(6.56, out.getBalance().doubleValue(), 0.0);
        verify(accountService, times(2)).save(in);
        verify(accountService, times(2)).save(out);
        verify(categoryService, never()).save(any());
        verify(repository, times(1)).save(operation);
    }

    @Test(expected = ServiceException.class)
    public void save_bothAccountsNull() throws ServiceException {
        Category category = new Category();
        category.setType(CategoryType.INCOME);
        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setCategory(category);
        try {
            service.save(operation);
        } catch (ServiceException e) {
            assertEquals(e.getMessage(), "Both the income account and outcome account are missing.");
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void save_categoryAndOneAccountNull() throws ServiceException {
        Account in = new Account();
        in.setBalance(BigDecimal.valueOf(6.09));
        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setInAccount(in);
        try {
            service.save(operation);
        } catch (ServiceException e) {
            assertEquals(e.getMessage(), "The category and at least one account are missing.");
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void save_allNotNull() throws ServiceException {
        Category category = new Category();
        category.setType(CategoryType.OUTCOME);

        Account in = new Account();
        in.setBalance(BigDecimal.valueOf(6.09));

        Account out = new Account();
        out.setBalance(BigDecimal.valueOf(10.01));

        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setCategory(category);
        operation.setInAccount(in);
        operation.setOutAccount(out);
        try {
            service.save(operation);
        } catch (ServiceException e) {
            String msg = "Category and both the accounts are not null. There is no way to determine an operation type.";
            assertEquals(e.getMessage(), msg);
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void save_wrongIncomeType() throws ServiceException {
        Category category = new Category();
        category.setType(CategoryType.INCOME);

        Account out = new Account();
        out.setBalance(BigDecimal.valueOf(10.01));

        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setCategory(category);
        operation.setOutAccount(out);
        try {
            service.save(operation);
        } catch (ServiceException e) {
            assertEquals(e.getMessage(), "Wrong category type for that operation: INCOME.");
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void save_wrongOutcomeType() throws ServiceException {
        Category category = new Category();
        category.setType(CategoryType.OUTCOME);

        Account in = new Account();
        in.setBalance(BigDecimal.valueOf(10.01));

        Operation operation = new Operation();
        operation.setId(11L);
        operation.setSum(BigDecimal.valueOf(3.45));
        operation.setCategory(category);
        operation.setInAccount(in);
        try {
            service.save(operation);
        } catch (ServiceException e) {
            assertEquals(e.getMessage(), "Wrong category type for that operation: OUTCOME.");
            throw e;
        }
    }
}