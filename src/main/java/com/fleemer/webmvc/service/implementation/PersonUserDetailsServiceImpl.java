package com.fleemer.webmvc.service.implementation;

import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.security.PersonDetails;
import com.fleemer.webmvc.service.PersonUserDetailsService;
import com.fleemer.webmvc.service.PersonService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonUserDetailsServiceImpl implements PersonUserDetailsService {
    private final PersonService service;

    @Autowired
    public PersonUserDetailsServiceImpl(PersonService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = service.findByEmail(s);
        if (person.isPresent()) {
            return new PersonDetails(person.get());
        }
        throw new UsernameNotFoundException("No person with such email.");
    }
}
