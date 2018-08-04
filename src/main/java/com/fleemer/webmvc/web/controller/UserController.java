package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.service.PersonService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final String USER_FORM_VIEW = "userForm";
    private final PersonService personService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(PersonService personService, BCryptPasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ModelAndView showCreateUserForm() {
        return new ModelAndView(USER_FORM_VIEW, "person", new Person());
    }

    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return USER_FORM_VIEW;
        }
        String email = person.getEmail();
        if (personService.findByEmail(email).isPresent()) {
            String message = "User with such email address already exists! Try again, please.";
            bindingResult.rejectValue("email", "email.alreadyExists", message);
            return USER_FORM_VIEW;
        }
        String hash = passwordEncoder.encode(person.getHash());
        person.setHash(hash);
        personService.save(person);
        return "redirect:/";
    }
}
