package com.infobip.kulendayz.infobip.service.cdp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailAddressDto {

    @JsonProperty("address")
    private String address;

}
