package com.infobip.kulendayz.infobip.service.cdp;

import com.infobip.kulendayz.infobip.service.cdp.dto.ContactInformationDto;
import com.infobip.kulendayz.infobip.service.cdp.dto.CreatePersonRequestDto;
import com.infobip.kulendayz.infobip.service.cdp.dto.CreatePersonResponseDto;
import com.infobip.kulendayz.infobip.service.cdp.dto.EmailAddressDto;
import com.infobip.kulendayz.users.integration.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdpService {

    private final RestTemplate restTemplate;

    @Value("${infobip.api.url}")
    private String apiBaseUrl;

    @Value("${infobip.api.key}")
    private String apiKey;

    public void createPerson(final UserCreatedEvent userCreatedEvent) throws CdpServiceException {

        try {
            final URI apiUri = new URI(apiBaseUrl + "/people/2/persons");

            final List<EmailAddressDto> emails = new ArrayList<>();
            emails.add(EmailAddressDto.builder()
                    .address(userCreatedEvent.getEmail())
                    .build());

            final ContactInformationDto contactInformationDto = ContactInformationDto.builder()
                    .email(emails)
                    .build();

            final CreatePersonRequestDto createPersonRequestDto = CreatePersonRequestDto.builder()
                    .firstName(userCreatedEvent.getFirstName())
                    .lastName(userCreatedEvent.getLastName())
                    .contactInformation(contactInformationDto)
                    .build();

            final HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "App " + apiKey);

            final HttpEntity<CreatePersonRequestDto> requestHttpEntity = new HttpEntity<>(createPersonRequestDto, headers);

            final ResponseEntity<CreatePersonResponseDto> createPersonResponseEntity = restTemplate.exchange(
                    apiUri, HttpMethod.POST, requestHttpEntity, CreatePersonResponseDto.class);

            if (!createPersonResponseEntity.getStatusCode().is2xxSuccessful()) {
                throw new CdpServiceException("Failed to create person on Infobip CDP");
            }

        } catch (URISyntaxException e) {
            throw new CdpServiceException("Failed to construct request URL", e);
        }
    }
}
