package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.service.PersonService;
import java.util.Optional;

public class CommonUtils {
    public static Person getPersonByEmail(PersonService service, String email) {
        Optional<Person> optional = service.findByEmail(email);
        if (!optional.isPresent()) {
            throw new NullPointerException("No such person: email=" + email);
        }
        return optional.get();
    }
}
