package com.infobip.kulendayz.infobip.service.cdp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContactInformationDto {

    @JsonProperty("email")
    private List<EmailAddressDto> email;

}
