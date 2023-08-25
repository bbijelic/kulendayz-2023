package com.infobip.kulendayz.auth.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "/auth/1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthServiceRestController {

    @PostMapping(value = "/login")
    public void login(){

    }

    @PostMapping(value = "/register")
    public void register(){

    }

    @GetMapping(value = "/me", consumes = MediaType.ALL_VALUE)
    public void me(){

    }

}
