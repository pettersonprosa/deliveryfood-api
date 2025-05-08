package com.deliveryfood.core.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DeliveryFood")
                .version("v1")
                .description("REST API do DeliveryFood")
                .license(new License()
                    .name("Apache 2.0")
                    .url("http//springdoc.com")
                    )
                ).externalDocs(new ExternalDocumentation()
                    .description("Delivery")
                    .url("https://deliveryABC.com"));

                
    }
}
