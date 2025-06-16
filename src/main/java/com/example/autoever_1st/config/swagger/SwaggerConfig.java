package com.example.autoever_1st.config.swagger;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI();
    }

    private Info apiInfo() {
        return new Info()
                .title("Autoever API")
                .description("Autoever API")
                .version("1.0.0");
    }

}
