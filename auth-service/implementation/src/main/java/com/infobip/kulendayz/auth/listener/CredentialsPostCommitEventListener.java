package com.infobip.kulendayz.auth.listener;

import com.infobip.kulendayz.auth.entity.CredentialsEntity;
import com.infobip.kulendayz.auth.integration.event.RegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostCommitInsertEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CredentialsPostCommitEventListener implements PostCommitInsertEventListener {

    private final RabbitTemplate rabbitTemplate;

    private final static String EXCHANGE = "amq.topic";

    private final static String REGISTRATION_EVENT_ROUTING_KEY = "registration";

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        try {

            final Object entity = postInsertEvent.getEntity();
            if (entity instanceof CredentialsEntity credentialsEntity) {
                log.debug("Handling user registration rabbitmq event on post commit of the user entity: {}", credentialsEntity);

                final RegistrationEvent registrationEvent = RegistrationEvent.builder()
                        .firstName(credentialsEntity.getFirstName())
                        .lastName(credentialsEntity.getLastName())
                        .email(credentialsEntity.getEmail())
                        .registeredAt(credentialsEntity.getRegisteredAt())
                        .build();

                // Send event to the rabbit
                rabbitTemplate.convertAndSend(EXCHANGE, REGISTRATION_EVENT_ROUTING_KEY, registrationEvent);
            }

        } catch (final Exception e) {
            log.error("Failed to send event message to the rabbitmq: {}", e.getMessage());
        }
    }

    @Override
    public void onPostInsertCommitFailed(PostInsertEvent postInsertEvent) {
        // Called after transaction is rolled back, nothing to do here.
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
