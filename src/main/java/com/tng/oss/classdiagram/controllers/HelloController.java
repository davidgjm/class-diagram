package com.tng.oss.classdiagram.controllers;

import com.tng.oss.classdiagram.domain.JClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public JClass hello() {
        log.info("Hit hello controller");
        return JClass.from(Principal.class);
    }
}
