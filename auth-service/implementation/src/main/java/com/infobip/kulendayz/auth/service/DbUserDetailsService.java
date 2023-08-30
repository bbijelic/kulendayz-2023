package com.infobip.kulendayz.auth.service;

import com.infobip.kulendayz.auth.entity.CredentialsEntity;
import com.infobip.kulendayz.auth.security.UserInfoDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final CredentialsService credentialsService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final CredentialsEntity credentialsEntity = credentialsService.findByEmail(email);
        return new UserInfoDetails(credentialsEntity);
    }
}
