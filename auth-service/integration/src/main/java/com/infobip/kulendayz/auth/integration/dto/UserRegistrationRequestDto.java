package com.infobip.kulendayz.auth.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {

    @Size(min = 1, max = 20)
    @JsonProperty("firstName")
    private String firstName;

    @Size(min = 1, max = 20)
    @JsonProperty("lastName")
    private String lastName;

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("password")
    private String password;

}
