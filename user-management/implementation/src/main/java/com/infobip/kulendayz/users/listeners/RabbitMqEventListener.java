package com.infobip.kulendayz.users.listeners;

import com.infobip.kulendayz.auth.integration.event.RegistrationEvent;
import com.infobip.kulendayz.users.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqEventListener {

    public static final String REGISTRATION_QUEUE = "user-management";

    private final UserManagementService userManagementService;

    @Transactional
    @RabbitListener(queues = REGISTRATION_QUEUE)
    public void handleUserRegistrationEvent(final RegistrationEvent registrationEvent) {
        log.debug("Handling user registration event: {}", registrationEvent);
        userManagementService.createUser(registrationEvent);
    }


}
