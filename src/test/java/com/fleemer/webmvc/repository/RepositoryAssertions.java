package com.fleemer.webmvc.repository;

import static org.hamcrest.Matchers.comparesEqualTo;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import java.io.Serializable;
import java.util.Iterator;
import org.junit.Assert;

class RepositoryAssertions {
    static void assertEquals(Account expected, Account actual) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.getCurrency(), actual.getCurrency());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getPerson(), actual.getPerson());
        Assert.assertThat(actual.getSum(), comparesEqualTo(expected.getSum()));
    }

    static void assertEquals(Person expected, Person actual) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getHash(), actual.getHash());
        assertIterableEquals(expected.getAccounts(), actual.getAccounts());
        assertIterableEquals(expected.getCategories(), actual.getCategories());
    }

    static void assertEquals(Category expected, Category actual) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.getPerson(), actual.getPerson());
    }

    static void assertEquals(Operation expected, Operation actual) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTime(), actual.getTime());
        assertEquals(expected.getAccount(), actual.getAccount());
        assertEquals(expected.getCategory(), actual.getCategory());
        Assert.assertThat(actual.getSum(), comparesEqualTo(expected.getSum()));
        Assert.assertThat(actual.getResult(), comparesEqualTo(expected.getResult()));
        Assert.assertEquals(expected.getUuid(), actual.getUuid());
        Assert.assertEquals(expected.getComment(), actual.getComment());
    }

    static <T extends Serializable> void assertIterableEquals(Iterable<T> expected, Iterable<T> actual) {
        if (expected == actual) {
            return;
        }
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Iterator<T> expectedIterator = expected.iterator();
        Iterator<T> actualIterator = actual.iterator();
        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            T expectedElement = expectedIterator.next();
            T actualElement = actualIterator.next();
            if (expectedElement == actualElement) {
                continue;
            }
            if (expectedElement instanceof Account) {
                assertEquals((Account) expectedElement, (Account) actualElement);
            } else if (expectedElement instanceof Person) {
                assertEquals((Person) expectedElement, (Person) actualElement);
            } else if (expectedElement instanceof Category) {
                assertEquals((Category) expectedElement, (Category) actualElement);
            } else if (expectedElement instanceof Operation) {
                assertEquals((Operation) expectedElement, (Operation) actualElement);
            } else {
                String msg = "Unknown class for comparison: ";
                throw new ClassCastException(msg + expectedElement.getClass().getCanonicalName());
            }
        }
        Assert.assertFalse(expectedIterator.hasNext());
        Assert.assertFalse(actualIterator.hasNext());
    }
}
