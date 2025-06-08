package com.bitbyashish.neighborhood_service_directory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Neighborhood Services Directory API")
                        .description("API documentation for the Neighborhood Services Directory")
                        .version("1.0"));
    }
}
