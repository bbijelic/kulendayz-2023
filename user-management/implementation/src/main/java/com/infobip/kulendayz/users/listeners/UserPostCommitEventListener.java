package com.infobip.kulendayz.users.listeners;

import com.infobip.kulendayz.users.entity.UserEntity;
import com.infobip.kulendayz.users.integration.event.UserCreatedEvent;
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
public class UserPostCommitEventListener implements PostCommitInsertEventListener {

    private final RabbitTemplate rabbitTemplate;

    private final static String EXCHANGE = "amq.topic";

    private final static String USER_CREATION_EVENT_ROUTING_KEY = "infobip";

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        try {

            final Object entity = postInsertEvent.getEntity();
            if (entity instanceof UserEntity userEntity) {
                log.debug("Handling user registration rabbitmq event on post commit of the user entity: {}", userEntity);

                final UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .email(userEntity.getEmail())
                        .build();

                // Send event to the rabbit
                rabbitTemplate.convertAndSend(EXCHANGE, USER_CREATION_EVENT_ROUTING_KEY, userCreatedEvent);
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
