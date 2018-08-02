package com.fleemer.webmvc.repository;

import static com.fleemer.webmvc.EntityCreator.createAccount;
import static com.fleemer.webmvc.EntityCreator.createCategory;
import static com.fleemer.webmvc.EntityCreator.createPerson;

import com.fleemer.webmvc.config.DataTestConfig;
import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.model.enums.Currency;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
@DatabaseSetup({PersonRepositoryTest.INIT_DB_PATH})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DataTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringRunner.class)
public class PersonRepositoryTest {
    private static final String CLASSPATH = "classpath:";
    private static final String DATASETS_PATH = CLASSPATH + "dbunit/person/";
    static final String INIT_DB_PATH = CLASSPATH + "dbunit/db_init.xml";

    private List<Person> people;

    @Autowired
    private PersonRepository repository;

    @Before
    public void setUp() {
        people = new RepositoryTestingPopulationClass().getPeople();
    }

    @Test
    public void existsById() {
        Assert.assertTrue(repository.existsById(1L));
        Assert.assertFalse(repository.existsById(11L));
    }

    @Test
    public void findById() {
        Optional<Person> optional = repository.findById(1L);
        Assert.assertTrue(optional.isPresent());
        RepositoryAssertions.assertEquals(people.get(0), optional.get());
    }

    @Test
    public void getOne() {
        RepositoryAssertions.assertEquals(people.get(1), repository.getOne(2L));
    }

    @Test
    public void findAllById() {
        List<Person> expected = List.of(people.get(0), people.get(2), people.get(3));
        List<Person> actual = repository.findAllById(List.of(56L, 1L, 4L, 30L, 3L));
        RepositoryAssertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void findAll() {
        RepositoryAssertions.assertIterableEquals(people, repository.findAll());
    }

    @Test
    public void findAll_sort() {
        List<Person> expected = List.of(people.get(0), people.get(2), people.get(3), people.get(1), people.get(4));
        List<Person> actual = repository.findAll(new Sort(Sort.Direction.ASC, "email"));
        RepositoryAssertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void count() {
        Assert.assertEquals(5L, repository.count());
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_new_without_accounts_and_categories.xml")
    public void save_newWithoutAccountsAndCategories() {
        Person person = createPerson(null, "test firstname", "test lastname", "test email", "test hash", null, null);
        repository.save(person);
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_new_with_accounts_and_categories.xml")
    public void save_newWithAccountsAndCategories() {
        Account newAccount = createAccount(null, AccountType.DEBT, Currency.RUB, "new account 1", new BigDecimal("0.01"), null);
        Category newCategory = createCategory(null, "new category", CategoryType.OUTCOME, null);
        Set<Account> accountSet = new HashSet<>();
        accountSet.add(newAccount);
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(newCategory);
        Person person = createPerson(null, "new firstname", "new lastname", "new email", "new hash", accountSet, categorySet);
        newAccount.setPerson(person);
        newCategory.setPerson(person);
        repository.save(person);
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_existing.xml")
    public void save_existing() {
        Person person = people.get(1);
        person.setEmail("Changed email");
        repository.save(person);
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "save_all.xml")
    public void saveAll() {
        Person newPerson1 = createPerson(null, "New Firstname1", "New Lastname1", "New email1", "New hash1", null, null);
        Person newPerson2 = createPerson(null, "New Firstname2", "New Lastname2", "New email2", "New hash2", null, null);
        Person person = people.get(1);
        person.setEmail("Changed email");
        repository.saveAll(List.of(newPerson1, newPerson2, person));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_by_id.xml")
    public void deleteById() {
        repository.deleteById(1L);
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete.xml")
    public void delete() {
        repository.delete(people.get(3));
        repository.flush();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteInBatch() {
        String message = "Batch deletion not supported for Person entity.";
        try {
            repository.deleteInBatch(List.of(people.get(2), people.get(0)));
        } catch (UnsupportedOperationException e) {
            Assert.assertEquals(message, e.getMessage());
            throw e;
        }
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_all_iterable.xml")
    public void deleteAll_iterable() {
        repository.deleteAll(List.of(people.get(3), people.get(4)));
        repository.flush();
    }

    @Test
    @ExpectedDatabase(value = DATASETS_PATH + "delete_all.xml")
    public void deleteAll() {
        repository.deleteAll();
        repository.flush();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteAllInBatch() {
        String message = "Batch deletion not supported for Person entity.";
        try {
            repository.deleteAllInBatch();
        } catch (UnsupportedOperationException e) {
            Assert.assertEquals(message, e.getMessage());
            throw e;
        }
    }
}