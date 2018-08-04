package com.fleemer.webmvc.web.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final String LOGIN_VIEW = "login";

    @GetMapping
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/accounts";// todo /
        }
        return LOGIN_VIEW;
    }
}
