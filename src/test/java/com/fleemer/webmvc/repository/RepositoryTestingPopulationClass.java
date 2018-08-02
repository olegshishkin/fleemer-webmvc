package com.fleemer.webmvc.repository;

import static com.fleemer.webmvc.EntityCreator.*;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.model.enums.Currency;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class RepositoryTestingPopulationClass {
    private List<Account> accounts;
    private List<Person> people;
    private List<Category> categories;
    private List<Operation> operations;

    RepositoryTestingPopulationClass() {
        accounts = getTestAccounts();
        people = getTestPeople();
        categories = getTestCategories();
        operations = getTestOperations();
        peopleCollectionInitialisation();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    private List<Person> getTestPeople() {
        Person p1 = createPerson(1L, "FirstName1", "LastName1", "mail100@mail.ma", "hash1", new HashSet<>(), new HashSet<>());
        Person p2 = createPerson(2L, "FirstName2", "LastName2", "mail95@mail.ma", "hash2", new HashSet<>(), new HashSet<>());
        Person p3 = createPerson(3L, "FirstName3", "LastName3", "mail3@mail.ma", "hash3", new HashSet<>(), new HashSet<>());
        Person p4 = createPerson(4L, "FirstName4", "LastName4", "mail5@mail.ma", "hash4", new HashSet<>(), new HashSet<>());
        Person p5 = createPerson(5L, "FirstName5", "LastName5", "mail@mail.ma", "hash5", new HashSet<>(), new HashSet<>());
        return List.of(p1, p2, p3, p4, p5);
    }

    private List<Account> getTestAccounts() {
        List<Person> people = getTestPeople();
        BigDecimal sum1 = new BigDecimal("00000000000000001012.1000000000");
        Account a1 = createAccount(1L, AccountType.CASH, Currency.RUB, "Cash", sum1, people.get(1));
        BigDecimal sum2 = new BigDecimal("00000000000000000333.4500000000");
        Account a2 = createAccount(2L, AccountType.BANK_ACCOUNT, Currency.USD, "Bank", sum2, people.get(3));
        BigDecimal sum3 = new BigDecimal("00000000000000001001.0200000000");
        Account a3 = createAccount(3L, AccountType.DEBT, Currency.RUB, "Wallet", sum3, people.get(0));
        return List.of(a1, a2, a3);
    }

    private List<Operation> getTestOperations() {
        List<Account> accounts = getTestAccounts();
        List<Category> categories = getTestCategories();
        ZoneId zoneId = ZoneId.of("Europe/Moscow");

        LocalDateTime ldt1 = LocalDateTime.of(2018, Month.MAY, 12, 11, 51, 12, 123457000);
        ZonedDateTime time1 = ZonedDateTime.of(ldt1, zoneId);
        UUID uuid1 = UUID.fromString("7f764fd6-5ca6-472d-aa68-3ee6be457f1c");
        Operation o1 = createOperation(1L, time1, accounts.get(1), categories.get(6), 4567.98, 4901.43, uuid1, "comment1");

        LocalDateTime ldt2 = LocalDateTime.of(2018, Month.MAY, 12, 11, 51, 14, 555564000);
        ZonedDateTime time2 = ZonedDateTime.of(ldt2, zoneId);
        Operation o2 = createOperation(2L, time2, accounts.get(0), categories.get(5), 334.09, 678.01, null, "comment2");

        LocalDateTime ldt3 = LocalDateTime.of(2018, Month.MAY, 12, 11, 51, 17, 987667000);
        ZonedDateTime time3 = ZonedDateTime.of(ldt3, zoneId);
        UUID uuid3 = UUID.fromString("6d394e4e-4ccf-4635-8c08-1409f9f1472e");
        Operation o3 = createOperation(3L, time3, accounts.get(2), categories.get(2), 8080.11, 9081.13, uuid3, "comment3");

        LocalDateTime ldt4 = LocalDateTime.of(2018, Month.MAY, 12, 11, 51, 34, 872350000);
        ZonedDateTime time4 = ZonedDateTime.of(ldt4, zoneId);
        Operation o4 = createOperation(4L, time4, accounts.get(2), categories.get(4), 61.32, 9019.81, null, "comment4");

        LocalDateTime ldt5 = LocalDateTime.of(2018, Month.MAY, 12, 11, 51, 54, 163840000);
        ZonedDateTime time5 = ZonedDateTime.of(ldt5, zoneId);
        Operation o5 = createOperation(5L, time5, accounts.get(1), categories.get(6), 543.0, 5444.43, null, "comment5");

        LocalDateTime ldt6 = LocalDateTime.of(2018, Month.MAY, 12, 11, 58, 2, 668000);
        ZonedDateTime time6 = ZonedDateTime.of(ldt6, zoneId);
        Operation o6 = createOperation(6L, time6, accounts.get(1), categories.get(6), 12.1, 5456.53, null, "comment6");

        LocalDateTime ldt7 = LocalDateTime.of(2018, Month.MAY, 12, 12, 3, 22, 987168000);
        ZonedDateTime time7 = ZonedDateTime.of(ldt7, zoneId);
        Operation o7 = createOperation(7L, time7, accounts.get(0), categories.get(5), 146.58, 531.43, null, "comment7");

        LocalDateTime ldt8 = LocalDateTime.of(2018, Month.MAY, 12, 12, 11, 52, 543589000);
        ZonedDateTime time8 = ZonedDateTime.of(ldt8, zoneId);
        UUID uuid8 = UUID.fromString("7b978d5b-3b84-431f-bbe4-6f31f573ac61");
        Operation o8 = createOperation(8L, time8, accounts.get(0), categories.get(3), 1176.92, -645.49, uuid8, "comment8");

        LocalDateTime ldt9 = LocalDateTime.of(2018, Month.MAY, 12, 12, 31, 9, 827384000);
        ZonedDateTime time9 = ZonedDateTime.of(ldt9, zoneId);
        Operation o9 = createOperation(9L, time9, accounts.get(0), categories.get(3), 6.03, -651.52, null, "comment9");

        return List.of(o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    private List<Category> getTestCategories() {
        List<Person> people = getTestPeople();
        Category c1 = createCategory(1L, "Car", CategoryType.OUTCOME, people.get(0));
        Category c2 = createCategory(2L, "Home", CategoryType.OUTCOME, people.get(2));
        Category c3 = createCategory(3L, "Salary", CategoryType.INCOME, people.get(0));
        Category c4 = createCategory(4L, "Car", CategoryType.OUTCOME, people.get(1));
        Category c5 = createCategory(5L, "Gifts", CategoryType.OUTCOME, people.get(0));
        Category c6 = createCategory(6L, "Home", CategoryType.OUTCOME, people.get(1));
        Category c7 = createCategory(7L, "Salary", CategoryType.INCOME, people.get(3));
        return List.of(c1, c2, c3, c4, c5, c6, c7);
    }

    private void peopleCollectionInitialisation() {
        Person person1 = people.get(0);
        Set<Account> accountSet1 = new HashSet<>(List.of(accounts.get(2)));
        person1.setAccounts(accountSet1);
        Set<Category> categorySet1 = new HashSet<>(List.of(categories.get(0), categories.get(2), categories.get(4)));
        person1.setCategories(categorySet1);

        Person person2 = people.get(1);
        Set<Account> accountSet2 = new HashSet<>(List.of(accounts.get(0)));
        person2.setAccounts(accountSet2);
        Set<Category> categorySet2 = new HashSet<>(List.of(categories.get(3), categories.get(5)));
        person2.setCategories(categorySet2);

        Set<Category> categorySet3 = new HashSet<>(List.of(categories.get(1)));
        people.get(2).setCategories(categorySet3);

        Person person4 = people.get(3);
        Set<Account> accountSet4 = new HashSet<>(List.of(accounts.get(1)));
        person4.setAccounts(accountSet4);
        Set<Category> categorySet4 = new HashSet<>(List.of(categories.get(6)));
        person4.setCategories(categorySet4);
    }
}
