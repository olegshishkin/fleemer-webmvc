package com.fleemer.webmvc.web.controller;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.service.CategoryService;
import com.fleemer.webmvc.service.PersonService;
import com.fleemer.webmvc.service.exception.ServiceException;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
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
@RequestMapping("/category")
public class CategoryController {
    private static final String ROOT_VIEW = "categories";
    private final CategoryService categoryService;
    private final PersonService personService;

    @Autowired
    public CategoryController(CategoryService categoryService, PersonService personService) {
        this.categoryService = categoryService;
        this.personService = personService;
    }

    @GetMapping
    public String categories(Model model, Principal principal) {
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        fillModel(model, categoryService.findAll(person));
        model.addAttribute("category", new Category());
        return ROOT_VIEW;
    }

    @Transactional
    @PostMapping("/new")
    public String newCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult, Model model,
                                Principal principal) throws ServiceException {
        Person person = personService.findByEmail(principal.getName()).orElseThrow();
        if (bindingResult.hasErrors()) {
            fillModel(model, categoryService.findAll(person));
            return ROOT_VIEW;
        }
        Optional<Category> lookedCategory = categoryService.findByNameAndPerson(category.getName(), person);
        if (lookedCategory.isPresent()) {
            String message = "Category with such name already exists! Try again, please.";
            bindingResult.rejectValue("name", "name.alreadyExists", message);
            fillModel(model, categoryService.findAll(person));
            return ROOT_VIEW;
        }
        category.setPerson(person);
        categoryService.save(category);
        return "redirect:/category";
    }

    private void fillModel(Model model, Iterable<Category> collection) {
        model.addAttribute("categories", collection);
        model.addAttribute("categoryTypes", CategoryType.values());
    }
}
