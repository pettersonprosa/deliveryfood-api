package com.deliveryfood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CryptConfig {

    @Bean
    public PasswordEncoder passworEncoder() {
        return new BCryptPasswordEncoder();
    }
}
