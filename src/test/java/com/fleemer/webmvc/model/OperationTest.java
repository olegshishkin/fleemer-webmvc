package com.fleemer.webmvc.model;

import static com.fleemer.webmvc.EntityCreator.createOperation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.*;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class OperationTest {
    private Operation o1;
    private Operation o2;

    @Before
    public void setUp() {
        Account a1 = new Account();
        a1.setId(1111L);
        Category c1 = new Category();
        c1.setId(3333L);
        LocalDateTime localDateTime1 = LocalDateTime.of(1990, Month.JANUARY, 1, 10, 20, 30);
        ZonedDateTime time1 = ZonedDateTime.of(localDateTime1, ZoneId.of("Asia/Tokyo"));
        UUID uuid1 = UUID.fromString("f4780c3e-eb8c-4e6e-994a-3e2b144b9e1e");
        o1 = createOperation(11L, time1, a1, c1, 123.12, 345.0, uuid1, "comment1");

        Account a2 = new Account();
        a2.setId(2222L);
        Category c2 = new Category();
        c2.setId(4444L);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, Month.MARCH, 8, 2, 11, 21);
        ZonedDateTime time2 = ZonedDateTime.of(localDateTime2, ZoneId.of("America/Phoenix"));
        UUID uuid2 = UUID.fromString("faadf71e-d25d-44b6-b017-a3ab21f1da3c");
        o2 = createOperation(22L, time2, a2, c2, 0.008, 1.87, uuid2, "comment2");
    }

    @Test
    public void equals_null() {
        assertNotEquals(new Operation(), null);
        assertEquals(new Operation(), new Operation());
        o1.setId(null);
        assertEquals(o1, new Operation());
    }

    @Test
    public void equals_notNull() {
        assertNotEquals(o1, o2);
        o2.setId(11L);
        assertEquals(o1, o2);
    }

    @Test
    public void hashCode_test() {
        int hash = o1.hashCode();
        assertNotEquals(hash, o2.hashCode());
        o2.setId(11L);
        assertEquals(hash, o2.hashCode());
    }
}