package com.infobip.kulendayz.infobip.api;

import com.infobip.kulendayz.infobip.integration.dto.MessageResponseDto;
import com.infobip.kulendayz.infobip.service.email.EmailMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(
        path = "/infobip/1/messages",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Transactional
public class MessagesRestController {

    private final EmailMessageService emailMessageService;

    @Transactional(readOnly = true)
    @GetMapping(consumes = MediaType.ALL_VALUE)
    public Collection<MessageResponseDto> getMessages() {
        return emailMessageService.getMessages();
    }

}
