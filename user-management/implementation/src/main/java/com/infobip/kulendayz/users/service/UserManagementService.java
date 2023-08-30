package com.infobip.kulendayz.users.service;

import com.infobip.kulendayz.auth.integration.event.RegistrationEvent;
import com.infobip.kulendayz.users.entity.UserEntity;
import com.infobip.kulendayz.users.integration.dto.UserResponseDto;
import com.infobip.kulendayz.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;

    public void createUser(final RegistrationEvent registrationEvent) {

        final UserEntity userEntity = UserEntity.builder()
                .email(registrationEvent.getEmail())
                .firstName(registrationEvent.getFirstName())
                .lastName(registrationEvent.getLastName())
                .registeredAt(registrationEvent.getRegisteredAt())
                .build();

        userRepository.save(userEntity);
    }

    public Collection<UserResponseDto> getUsers() {
        // Generally bad idea, should use paging instead
        return userRepository.findAll().stream()
                .map(userEntity -> UserResponseDto.builder()
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .email(userEntity.getEmail())
                        .registeredAt(userEntity.getRegisteredAt())
                        .build())
                .collect(Collectors.toList());
    }

    public UserResponseDto findUser(final String email) {
        final UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return UserResponseDto.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .registeredAt(userEntity.getRegisteredAt())
                .build();
    }

}
