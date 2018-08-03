package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.enums.AccountType;
import com.fleemer.webmvc.model.enums.Currency;
import com.fleemer.webmvc.service.AccountService;
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
@RequestMapping("/accounts")
public class AccountController {
    private static final String ACCOUNTS_VIEW = "accounts";
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ModelAndView showCreateAccountForm() {
        ModelAndView m = new ModelAndView(ACCOUNTS_VIEW);
        m.addObject("account", new Account());
        m.addObject("accountTypes", AccountType.values());
        m.addObject("currencies", Currency.values());
        m.addObject("accounts", accountService.findAllByPerson(null));//todo person
        return m;
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute Account account, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return ACCOUNTS_VIEW;
        }
        String name = account.getName();
        if (!accountService.findByNameAndPerson(name, null).isPresent()) {//todo person
            String errorMessage = "Category with such name already exists! Try again, please.";
            model.addAttribute("existenceError", errorMessage);
            return ACCOUNTS_VIEW;
        }
        accountService.save(account);
        return "redirect:/accounts";
    }
}
