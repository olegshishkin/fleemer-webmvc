package com.fleemer.webmvc.repository;

import static com.fleemer.webmvc.EntityCreator.createOperation;

import com.fleemer.webmvc.config.DataTestConfig;
import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@Transactional
@DatabaseSetup({OperationRepositoryTest.INIT_DB_PATH})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DataTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringRunner.class)
public class OperationRepositoryTest {
    private static final String CLASSPATH = "classpath:";
    private static final String DATASETS_PATH = CLASSPATH + "dbunit/operation/";
    static final String INIT_DB_PATH = CLASSPATH + "dbunit/db_init.xml";

    private List<Account> accounts;
    private List<Category> categories;
    private List<Operation> operations;

    @Autowired
    private OperationRepository repository;

    @Before
    public void setUp() {
        RepositoryTestingPopulationClass populationClass = new RepositoryTestingPopulationClass();
        accounts = populationClass.getAccounts();
        categories = populationClass.getCategories();
        operations = populationClass.getOperations();
    }

    @Test
    public void existsById() {
        Assert.assertTrue(repository.existsById(1L));
        Assert.assertFalse(repository.existsById(11L));
    }

    @Test
    public void findById() {
        Optional<Operation> optional = repository.findById(1L);
        Assert.assertTrue(optional.isPresent());
        RepositoryAssertions.assertEquals(operations.get(0), optional.get());
    }

    @Test
    public void getOne() {
        RepositoryAssertions.assertEquals(operations.get(1), repository.getOne(2L));
    }

    @Test
    public void findAllById() {
        List<Operation> expected = List.of(operations.get(0), operations.get(1), operations.get(3), operations.get(7));
        List<Operation> actual = repository.findAllById(List.of(56L, 1L, 2L, 58L, 30L, 83L, 8L, 654L, 4L));
        RepositoryAssertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void findAll() {
        RepositoryAssertions.assertIterableEquals(operations, repository.findAll());
    }

    @Test
    public void findAll_sort() {
        List<Operation> expected = new ArrayList<>();
        expected.add(operations.get(2));
        expected.add(operations.get(3));
        expected.add(operations.get(5));
        expected.add(operations.get(4));
        expected.add(operations.get(0));
        expected.add(operations.get(1));
        expected.add(operations.get(6));
        expected.add(operations.get(7));
        expected.add(operations.get(8));
        List<Operation> actual = repository.findAll(new Sort(Sort.Direction.DESC, "result"));
        RepositoryAssertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void count() {
        Assert.assertEquals(9L, repository.count());
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_new.xml")
    public void save_new() {
        LocalDateTime localDateTime = LocalDateTime.of(2000, Month.FEBRUARY, 4, 3, 21, 1, 98000);
        ZonedDateTime time = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Moscow"));
        UUID uuid = UUID.fromString("269c4f87-ac8d-418d-9f67-26b233b285e8");
        repository.save(createOperation(null, time, accounts.get(1), categories.get(6), 7.98, -7.98, uuid, "new comment"));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_existing.xml")
    public void save_existing() {
        Operation operation = operations.get(3);
        operation.setComment("Changed comment");
        repository.save(operation);
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_all.xml")
    public void saveAll() {
        LocalDateTime ldt1 = LocalDateTime.of(2018, Month.MAY, 14, 12, 20, 11, 87657000);
        ZonedDateTime time1 = ZonedDateTime.of(ldt1, ZoneId.of("Europe/Moscow"));
        UUID uuid1 = UUID.fromString("269c4f87-ac8d-418d-9f67-26b233b285e8");
        Operation o1 = createOperation(null, time1, accounts.get(1), categories.get(6), 7.98, 7.98, uuid1, "new comment1");

        LocalDateTime ldt2 = LocalDateTime.of(2018, Month.MAY, 14, 10, 20, 11, 444000);
        ZonedDateTime time2 = ZonedDateTime.of(ldt2, ZoneId.of("Europe/Moscow"));
        Operation o2 = createOperation(null, time2, accounts.get(0), categories.get(1), -17.98, -74.938, null, "new comment2");

        Operation o = operations.get(3);
        o.setComment("Changed comment");
        repository.saveAll(List.of(o1, o2, o));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_by_id.xml")
    public void deleteById() {
        repository.deleteById(3L);
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete.xml")
    public void delete() {
        repository.delete(operations.get(3));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_in_batch.xml")
    public void deleteInBatch() {
        repository.deleteInBatch(List.of(operations.get(3), operations.get(1)));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_all_iterable.xml")
    public void deleteAll_iterable() {
        repository.deleteAll(List.of(operations.get(2), operations.get(8)));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_all.xml")
    public void deleteAll() {
        repository.deleteAll();
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_all_in_batch.xml")
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
        repository.flush();
    }
}