package com.fleemer.webmvc.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    private static final String ROOT_VIEW = "error";

    @ExceptionHandler(Exception.class)
    public String showErrorPage() {
        return ROOT_VIEW;
    }
}
