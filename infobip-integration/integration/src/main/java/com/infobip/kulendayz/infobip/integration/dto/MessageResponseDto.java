package com.infobip.kulendayz.infobip.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class MessageResponseDto {

    @JsonProperty("email")
    private String email;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("content")
    private String content;

    @JsonProperty("sentAt")
    private OffsetDateTime sentAt;

    @JsonProperty("infobipMessageId")
    private String infobipMessageId;

}
