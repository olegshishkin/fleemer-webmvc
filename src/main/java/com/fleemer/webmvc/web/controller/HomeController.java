package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Account;
import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Operation;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.service.*;
import com.fleemer.webmvc.service.exception.ServiceException;
import com.fleemer.webmvc.web.form.OperationForm;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String home(Model model, Principal principal) {
        model.addAttribute("operation", new OperationForm());
        Person person = getCurrentPerson(principal);
        fillModel(model, person);
        return ROOT_VIEW;
    }

    @PostMapping("/operation/new")
    public String newOperation(@Valid @ModelAttribute OperationForm form, BindingResult result, Model model,
                               Principal principal) throws ServiceException {
        Person person = getCurrentPerson(principal);
        fillModel(model, person);
        if (result.hasErrors()) {
            return ROOT_VIEW;
        }
        Operation operation = getFilledOperation(form, person);
        operationService.save(operation);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/balance")
    public double balance(Principal principal) {
        Person person = getCurrentPerson(principal);
        return accountService.getTotalBalance(person).doubleValue();
    }

    @ResponseBody
    @GetMapping(value = "/operation", params = {"page", "size"})
    public List<Operation> getOperations(@RequestParam("page") int page, @RequestParam("size") int size,
                                         Principal principal) {
        return operationService.findAll(getCurrentPerson(principal), page, size);
    }

    @ResponseBody
    @GetMapping("/accountsList")
    public List<Account> getAccounts(Principal principal) {
        Person person = getCurrentPerson(principal);
        return accountService.findAll(person);
    }

    private Person getCurrentPerson(Principal principal) {
        return personService.findByEmail(principal.getName()).orElseThrow();
    }

    private void fillModel(Model model, Person person) {
        model.addAttribute("accounts", accountService.findAll(person));
        model.addAttribute("categories", categoryService.findAll(person));
        model.addAttribute("operations", operationService.findAll(person));
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
}
