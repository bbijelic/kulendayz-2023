package com.infobip.kulendayz.users.api;

import com.infobip.kulendayz.users.integration.dto.UserResponseDto;
import com.infobip.kulendayz.users.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(
        path = "/user/1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Transactional
public class UserManagementRestController {

    private final UserManagementService userManagementService;

    @GetMapping(consumes = MediaType.ALL_VALUE)
    @Transactional(readOnly = true)
    public Collection<UserResponseDto> getUsers() {
        return userManagementService.getUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/me", consumes = MediaType.ALL_VALUE)
    public UserResponseDto me(final Principal principal) {
        return userManagementService.findUser(principal.getName());
    }

}
