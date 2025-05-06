package com.deliveryfood.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

    // @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}")
    // private String introspectionUri;
    
    
    // @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    // private String clientId;
    
    // @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    // private String clientSecret;
    
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/oauth2/**").authenticated()
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // configuração padrão
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                    .jwkSetUri(jwkSetUri) // substitua pelo URI do seu JWK Set
                )
            );

        return http.build();
    }

}
