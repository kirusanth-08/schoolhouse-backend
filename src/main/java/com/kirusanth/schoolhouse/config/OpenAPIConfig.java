package com.kirusanth.schoolhouse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI schoolhouseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Schoolhouse API")
                        .description("API documentation for Schoolhouse application")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Schoolhouse Team")
                                .email("support@schoolhouse.com")));
    }
}