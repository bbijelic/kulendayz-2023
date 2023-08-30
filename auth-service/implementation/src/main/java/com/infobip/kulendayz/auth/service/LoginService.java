package com.infobip.kulendayz.auth.service;

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
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @SuppressWarnings("unchecked")
    public String login(final String email, final String password) {

        // Authenticate
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        if (authentication.isAuthenticated()) {
            // Granted authorities (roles) for JWT claims
            final Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
            // Create token and return in response body
            return jwtService.createToken(jwtService.toClaims(grantedAuthorities), email);
        } else {
            // Unknown user -> unauthorized
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

}
