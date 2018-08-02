package com.fleemer.webmvc;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.model.enums.Currency;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

public class EntityCreator {
    public static Account createAccount(Long id, AccountType type, Currency currency, String name, BigDecimal sum,
                                        Person person) {
        Account a = new Account();
        a.setId(id);
        a.setType(type);
        a.setCurrency(currency);
        a.setName(name);
        a.setSum(sum);
        a.setPerson(person);
        return a;
    }

    public static Category createCategory(Long id, String name, CategoryType type, Person person) {
        Category c = new Category();
        c.setId(id);
        c.setPerson(person);
        c.setName(name);
        c.setType(type);
        return c;
    }

    public static Operation createOperation(Long id, ZonedDateTime time, Account account, Category category, double sum,
                                            double result, UUID uuid, String comment) {
        Operation o = new Operation();
        o.setId(id);
        o.setAccount(account);
        o.setCategory(category);
        o.setTime(time);
        o.setUuid(uuid);
        o.setSum(new BigDecimal(String.valueOf(sum)));
        o.setResult(new BigDecimal(String.valueOf(result)));
        o.setComment(comment);
        return o;
    }

    public static Person createPerson(Long id, String firstName, String lastName, String email, String hash,
                                      Set<Account> accounts, Set<Category> categories) {
        Person p = new Person();
        p.setId(id);
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setHash(hash);
        p.setAccounts(accounts);
        p.setCategories(categories);
        return p;
    }
}
