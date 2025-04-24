package com.deliveryfood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
        http
            // Define regras de autorização para as requisições HTTP
            .authorizeHttpRequests(auth -> auth
                // Libera o acesso público (sem autenticação) para a rota /v1/cozinhas/**
                .requestMatchers("/v1/cozinhas/**").permitAll()
                // Exige autenticação para qualquer outra requisição
                .anyRequest().authenticated()
            )

            // Define a política de gerenciamento de sessões
            .sessionManagement(session -> session
                // Define que a aplicação não criará nem usará sessões HTTP (modo stateless, ideal para APIs REST)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Desativa a proteção CSRF (Cross-Site Request Forgery)
            // Recomendado apenas para APIs REST stateless (que não usam cookies)
            .csrf(csrf -> csrf.disable())

            // Ativa autenticação HTTP Basic de forma funcional (sem usar métodos depreciados)
            .httpBasic(httpBasic -> {}); // Forma moderna de ativar httpBasic, mesmo sem configurações extras

        // Constrói e retorna o SecurityFilterChain com as configurações acima
        return http.build();
	}

}
