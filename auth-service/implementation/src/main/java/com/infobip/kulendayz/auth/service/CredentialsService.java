package com.infobip.kulendayz.auth.service;

import com.infobip.kulendayz.auth.entity.CredentialsEntity;
import com.infobip.kulendayz.auth.repository.CredentialsRepository;
import com.infobip.kulendayz.auth.tokens.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;

    public CredentialsEntity findByEmail(final String email) {
        return credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

}
