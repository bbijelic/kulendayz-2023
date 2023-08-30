package com.infobip.kulendayz.infobip.listeners;

import com.infobip.kulendayz.infobip.service.cdp.CdpService;
import com.infobip.kulendayz.infobip.service.cdp.CdpServiceException;
import com.infobip.kulendayz.infobip.service.email.EmailMessageService;
import com.infobip.kulendayz.infobip.service.email.EmailMessageServiceException;
import com.infobip.kulendayz.users.integration.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqEventListener {

    public static final String INFOBIP_INTEGRATION_QUEUE = "infobip-integration";

    private final CdpService cdpService;

    private final EmailMessageService emailMessageService;

    @Transactional
    @RabbitListener(queues = INFOBIP_INTEGRATION_QUEUE)
    public void handleUserCreatedEvent(final UserCreatedEvent userCreatedEvent) {
        log.debug("Handling user creation event: {}", userCreatedEvent);

        try {

            //cdpService.createPerson(userCreatedEvent);
            emailMessageService.sendEmail(userCreatedEvent);

        } catch (final CdpServiceException | EmailMessageServiceException e) {
            log.warn("Failed to handle user creation event", e);
        }
    }

}
