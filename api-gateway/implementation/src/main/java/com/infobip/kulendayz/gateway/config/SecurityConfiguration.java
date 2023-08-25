package com.infobip.kulendayz.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private static final String ACTUATOR_PATH_MATCHER_PATTERN = "/actuator/**";

    @Bean
    public SecurityWebFilterChain resourceServerWebFilterChain(final ServerHttpSecurity http) {

        http.authorizeExchange(
                authorize -> authorize.pathMatchers(
                                ACTUATOR_PATH_MATCHER_PATTERN
                        ).permitAll()
                        .anyExchange().authenticated());
        return http.build();
    }
}
