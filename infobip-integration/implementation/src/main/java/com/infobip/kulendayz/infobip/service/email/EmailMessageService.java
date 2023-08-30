package com.infobip.kulendayz.infobip.service.email;

import com.infobip.ApiException;
import com.infobip.api.EmailApi;
import com.infobip.kulendayz.infobip.entity.MessageEntity;
import com.infobip.kulendayz.infobip.integration.dto.MessageResponseDto;
import com.infobip.kulendayz.infobip.repository.MessageRepository;
import com.infobip.kulendayz.users.integration.event.UserCreatedEvent;
import com.infobip.model.EmailResponseDetails;
import com.infobip.model.EmailSendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageService {

    @Value("${infobip.registration.subject}")
    private String subject;

    @Value("${infobip.registration.sender}")
    private String sender;

    @Value("${infobip.registration.recipient}")
    private String recipient;

    private final MessageRepository messageRepository;

    private final EmailApi emailApi;

    public void sendEmail(final UserCreatedEvent userCreatedEvent) throws EmailMessageServiceException {

        // Check if message already sent?
        final Optional<MessageEntity> messageEntityOptional
                = messageRepository.findByEmail(userCreatedEvent.getEmail());

        if (messageEntityOptional.isPresent()) {
            log.warn("Message to {} already sent, skipping", userCreatedEvent.getEmail());
            return;
        }

        final List<String> to = new ArrayList<>(List.of(recipient));

        final String emailContent = String.format(
                "User %s %s just registered at Friday Morning Workshop @ KulenDayz 2023",
                userCreatedEvent.getFirstName(), userCreatedEvent.getLastName());

        try {
            final EmailSendResponse response = emailApi
                    .sendEmail(to)
                    .from(sender)
                    .subject(subject)
                    .text(emailContent)
                    .execute();

            log.debug("Email sending response: {}", response);

            final EmailResponseDetails emailResponseDetails = response.getMessages().get(0);

            final MessageEntity messageEntity = messageRepository.save(MessageEntity.builder()
                    .email(userCreatedEvent.getEmail())
                    .bulkId(response.getBulkId())
                    .messageId(emailResponseDetails.getMessageId())
                    .content(emailContent)
                    .subject(subject)
                    .sentAt(OffsetDateTime.now())
                    .build());

            log.debug("Email message: {}", messageEntity);

        } catch (ApiException e) {
            throw new EmailMessageServiceException("Failed to send an email via Infobip", e);
        }

    }

    public Collection<MessageResponseDto> getMessages() {
        return messageRepository.findAll().stream()
                .map(messageEntity -> MessageResponseDto.builder()
                        .infobipMessageId(messageEntity.getMessageId())
                        .email(messageEntity.getEmail())
                        .subject(messageEntity.getSubject())
                        .content(messageEntity.getContent())
                        .sentAt(messageEntity.getSentAt())
                        .build())
                .collect(Collectors.toList());
    }

}
