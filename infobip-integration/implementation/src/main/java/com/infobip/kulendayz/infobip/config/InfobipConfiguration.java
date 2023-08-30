package com.infobip.kulendayz.infobip.config;

import com.infobip.ApiClient;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.EmailApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfobipConfiguration {

    @Value("${infobip.api.url}")
    private String apiUrl;

    @Value("${infobip.api.key}")
    private String apiKey;

    @Bean
    public ApiClient apiClient() {
        return ApiClient.forApiKey(ApiKey.from(apiKey))
                .withBaseUrl(BaseUrl.from(apiUrl))
                .build();
    }

    @Bean
    public EmailApi emailApi(final ApiClient apiClient) {
        return new EmailApi(apiClient);
    }

}
