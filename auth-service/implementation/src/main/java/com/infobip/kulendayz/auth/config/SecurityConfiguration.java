package com.infobip.kulendayz.auth.config;

import com.infobip.kulendayz.auth.service.CredentialsService;
import com.infobip.kulendayz.auth.service.DbUserDetailsService;
import com.infobip.kulendayz.auth.tokens.jwt.JwtAuthFilter;
import com.infobip.kulendayz.auth.tokens.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private static final String ACTUATOR_PATH_MATCHER_PATTERN = "/actuator/**";
    private static final String LOGIN_PATH_MATCHER_PATTERN = "/auth/*/login";
    private static final String REGISTER_PATH_MATCHER_PATTERN = "/auth/*/register";

    private static final String ERROR_PATH_MATCHER_PATTERN = "/error/**";

    @Bean
    public JwtAuthFilter jwtAuthFilter(final JwtService jwtService){
        return new JwtAuthFilter(jwtService);
    }

    @Bean
    public UserDetailsService userDetailsService(final CredentialsService credentialsService){
        return new DbUserDetailsService(credentialsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider(final UserDetailsService userDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http, AuthenticationProvider daoAuthenticationProvider, JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                ERROR_PATH_MATCHER_PATTERN,
                                ACTUATOR_PATH_MATCHER_PATTERN,
                                LOGIN_PATH_MATCHER_PATTERN,
                                REGISTER_PATH_MATCHER_PATTERN)
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
