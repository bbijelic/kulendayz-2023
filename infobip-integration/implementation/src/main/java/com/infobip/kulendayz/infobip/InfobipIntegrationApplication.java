package com.infobip.kulendayz.infobip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.infobip.kulendayz")
public class InfobipIntegrationApplication {

    public static void main(String[] args){
        SpringApplication.run(InfobipIntegrationApplication.class, args);
    }

}
