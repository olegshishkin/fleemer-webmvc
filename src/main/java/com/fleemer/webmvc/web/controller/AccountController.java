package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.Currency;
import com.fleemer.webmvc.service.AccountService;
import com.fleemer.webmvc.service.PersonService;
import com.fleemer.webmvc.service.exception.ServiceException;
import java.security.Principal;
import java.util.Optional;
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
@RequestMapping("/account")
public class AccountController {
    private static final String ROOT_VIEW = "accounts";
    private final AccountService accountService;
    private final PersonService personService;

    @Autowired
    public AccountController(AccountService accountService, PersonService personService) {
        this.accountService = accountService;
        this.personService = personService;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public String accounts(Model model, Principal principal) {
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        fillModel(model, person.getAccounts());
        model.addAttribute("account", new Account());
        return ROOT_VIEW;
    }

    @Transactional
    @PostMapping("/new")
    public String newAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult, Model model,
                             Principal principal) throws ServiceException {
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        if (bindingResult.hasErrors()) {
            fillModel(model, person.getAccounts());
            return ROOT_VIEW;
        }
        Optional<Account> lookedAccount = accountService.findByNameAndPerson(account.getName(), person);
        if (lookedAccount.isPresent()) {
            String message = "Account with such name already exists! Try again, please.";
            bindingResult.rejectValue("name", "name.alreadyExists", message);
            fillModel(model, person.getAccounts());
            return ROOT_VIEW;
        }
        account.setPerson(person);
        accountService.save(account);
        return "redirect:/account";
    }

    private void fillModel(Model model, Iterable<Account> collection) {
        Hibernate.initialize(collection);
        model.addAttribute("accounts", collection);
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("currencies", Currency.values());
    }
}
