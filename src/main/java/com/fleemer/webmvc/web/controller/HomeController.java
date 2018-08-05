package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.service.*;
import com.fleemer.webmvc.service.exception.ServiceException;
import com.fleemer.webmvc.web.form.OperationForm;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        model.addAttribute("operation", new OperationForm());
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        fillModel(model, person);
        return ROOT_VIEW;
    }

    @Transactional
    @PostMapping("/operation/new")
    public String newOperation(@Valid @ModelAttribute OperationForm form, BindingResult result, Model model,
                               Principal principal) throws ServiceException {
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        fillModel(model, person);
        if (result.hasErrors()) {
            return ROOT_VIEW;
        }
        Operation operation = getFilledOperation(form, person);
        operationService.save(operation);
        return "redirect:/";
    }

    @GetMapping("totalAmount")
    public double getTotalAmount(Principal principal) {
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        BigDecimal total = BigDecimal.ZERO;
        person.getAccounts().forEach(x -> total.add(x.getBalance()));
        return total.doubleValue();
    }

    private void fillModel(Model model, Person person) {
        List<String> accounts = new ArrayList<>();
        person.getAccounts().forEach(x -> accounts.add(x.getName()));
        List<String> categories = new ArrayList<>();
        person.getCategories().forEach(x -> categories.add(x.getName()));
        fillModel(model, "accounts", accounts);
        fillModel(model, "categories", categories);
        fillModel(model, "operations", operationService.findAll(person));
    }

    private Operation getFilledOperation(OperationForm form, Person person) {
        Account inAccount = accountService.findByNameAndPerson(form.getInAccountName(), person).orElse(null);
        Account outAccount = accountService.findByNameAndPerson(form.getOutAccountName(), person).orElse(null);
        Category category = categoryService.findByNameAndPerson(form.getCategoryName(), person).orElse(null);
        Operation operation = form.getOperation();
        operation.setInAccount(inAccount);
        operation.setOutAccount(outAccount);
        operation.setCategory(category);
        return operation;
    }

    private static void fillModel(Model model, String attribute, Iterable<?> collection) {
        Hibernate.initialize(collection);
        model.addAttribute(attribute, collection);
    }
}
