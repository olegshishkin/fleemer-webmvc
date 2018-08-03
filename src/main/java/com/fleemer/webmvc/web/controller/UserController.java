package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.service.PersonService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ModelAndView showCreateUserForm() {
        return new ModelAndView(USER_FORM_VIEW, "person", new Person());
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute Person person, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return USER_FORM_VIEW;
        }
        String email = person.getEmail();
        if (!personService.findByEmail(email).isPresent()) {
            String errorMessage = "User with such email address already exists! Try again, please.";
            model.addAttribute("existenceError", errorMessage);
            return USER_FORM_VIEW;
        }
        personService.save(person);
        return "redirect:/";
    }
}
