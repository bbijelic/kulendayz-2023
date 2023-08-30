package com.infobip.kulendayz.auth.service;

import com.infobip.kulendayz.auth.entity.CredentialsEntity;
import com.infobip.kulendayz.auth.integration.dto.RoleEnum;
import com.infobip.kulendayz.auth.integration.dto.UserRegistrationRequestDto;
import com.infobip.kulendayz.auth.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(final UserRegistrationRequestDto userRegistrationRequestDto) {
        final CredentialsEntity credentialsEntity = CredentialsEntity.builder()
                .uid(UUID.randomUUID())
                .email(userRegistrationRequestDto.getEmail())
                .registeredAt(OffsetDateTime.now())
                .password(passwordEncoder.encode(userRegistrationRequestDto.getPassword()))
                .roles(Set.of(RoleEnum.USER))
                .firstName(userRegistrationRequestDto.getFirstName())
                .lastName(userRegistrationRequestDto.getLastName())
                .build();

        credentialsRepository.save(credentialsEntity);
    }

}
