package com.infobip.kulendayz.auth.api.controller;

import com.infobip.kulendayz.auth.integration.dto.UserLoginRequestDto;
import com.infobip.kulendayz.auth.integration.dto.UserRegistrationRequestDto;
import com.infobip.kulendayz.auth.service.LoginService;
import com.infobip.kulendayz.auth.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(
        path = "/auth/1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Transactional
public class AuthServiceRestController {

    private final LoginService loginService;

    private final RegistrationService registrationService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login(@RequestBody @Valid final UserLoginRequestDto userLoginRequestDto) {
        return loginService.login(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/register")
    public void register(@RequestBody @Valid final UserRegistrationRequestDto userRegistrationRequestDto) {
        registrationService.register(userRegistrationRequestDto);
    }

}
