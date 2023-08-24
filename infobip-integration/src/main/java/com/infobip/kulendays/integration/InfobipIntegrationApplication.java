package com.infobip.kulendays.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InfobipIntegrationApplication {

    public static void main(String[] args){
        SpringApplication.run(InfobipIntegrationApplication.class, args);
    }

}
