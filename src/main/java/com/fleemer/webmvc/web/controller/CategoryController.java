package com.fleemer.webmvc.web.controller;

import static com.fleemer.webmvc.web.controller.CommonUtils.getPersonByEmail;

import com.fleemer.webmvc.model.Category;
import com.fleemer.webmvc.model.Person;
import com.fleemer.webmvc.model.enums.CategoryType;
import com.fleemer.webmvc.service.CategoryService;
import com.fleemer.webmvc.service.PersonService;
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

    @Transactional(readOnly = true)
    @GetMapping
    public String categories(Model model, Principal principal) {
        Person person = getPersonByEmail(personService, principal.getName());
        populateModel(model, person.getCategories());
        model.addAttribute("category", new Category());
        return ROOT_VIEW;
    }

    @Transactional
    @PostMapping("/new")
    public String newCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult, Model model,
                                Principal principal) {
        Person person = getPersonByEmail(personService, principal.getName());
        if (bindingResult.hasErrors()) {
            populateModel(model, person.getCategories());
            return ROOT_VIEW;
        }
        Optional<Category> lookedCategory = categoryService.findByNameAndPerson(category.getName(), person);
        if (lookedCategory.isPresent()) {
            String message = "Category with such name already exists! Try again, please.";
            bindingResult.rejectValue("name", "name.alreadyExists", message);
            populateModel(model, person.getCategories());
            return ROOT_VIEW;
        }
        category.setPerson(person);
        categoryService.save(category);
        return "redirect:/category";
    }

    private void populateModel(Model model, Iterable<Category> collection) {
        Hibernate.initialize(collection);
        model.addAttribute("categories", collection);
        model.addAttribute("categoryTypes", CategoryType.values());
    }
}
