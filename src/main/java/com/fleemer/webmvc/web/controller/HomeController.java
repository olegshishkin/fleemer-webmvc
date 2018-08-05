package com.fleemer.webmvc.web.controller;

import static com.fleemer.webmvc.web.controller.CommonUtils.getPersonByEmail;

import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.service.AccountService;
import com.fleemer.webmvc.service.CategoryService;
import com.fleemer.webmvc.service.OperationService;
import com.fleemer.webmvc.service.PersonService;
import java.security.Principal;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String ROOT_VIEW = "index";
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final PersonService personService;
    private final OperationService operationService;

    @Autowired
    public HomeController(AccountService accountService, CategoryService categoryService, PersonService personService,
                          OperationService operationService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.personService = personService;
        this.operationService = operationService;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public String home(Model model, Principal principal) {
        model.addAttribute("operation", new Operation());
        Person person = getPersonByEmail(personService, principal.getName());
        populateModel(model, "accounts", person.getAccounts());
        populateModel(model, "categories", person.getCategories());
        populateModel(model, "operations", operationService.findAll(person));
        return ROOT_VIEW;
    }

    private static void populateModel(Model model, String attribute, Iterable<?> collection) {
        Hibernate.initialize(collection);
        model.addAttribute(attribute, collection);
    }
}
