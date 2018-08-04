package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.Currency;
import com.fleemer.webmvc.service.AccountService;
import com.fleemer.webmvc.service.PersonService;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final String ACCOUNTS_VIEW = "accounts";
    private final AccountService accountService;
    private final PersonService personService;

    @Autowired
    public AccountController(AccountService accountService, PersonService personService) {
        this.accountService = accountService;
        this.personService = personService;
    }

    @GetMapping
    public String showCreateAccountForm(ModelMap model, Principal principal) {
        model.addAttribute("account", new Account());
        modelPopulate(model, principal);
        return ACCOUNTS_VIEW;
    }

    private void modelPopulate(ModelMap model, Principal principal) {
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("accounts", accountService.findAllByPersonEmail(principal.getName()));
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult, ModelMap model,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            logger.debug("Unsuccessful form validation. Object: " + account.toString());
            modelPopulate(model, principal);
            return ACCOUNTS_VIEW;
        }
        String name = account.getName();
        Optional<Person> person = personService.findByEmail(principal.getName());
        if (!person.isPresent()) {
            return "redirect:/";
        }
        account.setPerson(person.get());
        if (accountService.findByNameAndPerson(name, person.get()).isPresent()) {
            String message = "Category with such name already exists! Try again, please.";
            bindingResult.rejectValue("name", "name.alreadyExists", message);
            logger.debug("Unsuccessful form validation. Object: " + account.toString());
            modelPopulate(model, principal);
            return ACCOUNTS_VIEW;
        }
        accountService.save(account);
        logger.debug("Account was created. Id: " + account.getId());
        return "redirect:/" + ACCOUNTS_VIEW;
    }
}
