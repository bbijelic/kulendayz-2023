package com.infobip.kulendayz.auth.tokens.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {

    private final String subject;

    private final Collection<GrantedAuthority> authorities;

    @SuppressWarnings("unchecked")
    public JwtUserDetails(final String token, final JwtService jwtService) {
        this.subject = jwtService.getSubject(token);
        this.authorities = jwtService.toGrantedAuthorities(token);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return subject;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
