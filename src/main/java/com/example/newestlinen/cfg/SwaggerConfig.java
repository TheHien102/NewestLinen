package com.example.newestlinen.cfg;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Linen A 2022 v1 API")
                        .version("1.0")
                        .description("Linen A 2022 v1 API")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi qrCodeApi() {
        return GroupedOpenApi.builder()
                .group("landingis-controller")
                .pathsToMatch("/v1/**")
                .build();
    }

}
