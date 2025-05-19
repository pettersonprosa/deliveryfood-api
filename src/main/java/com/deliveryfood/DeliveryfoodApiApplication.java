package com.deliveryfood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.deliveryfood.core.io.Base64ProtocolResolver;
import com.deliveryfood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableRedisHttpSession
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class DeliveryfoodApiApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        var app = new SpringApplication(DeliveryfoodApiApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
        // SpringApplication.run(DeliveryfoodApiApplication.class, args);
    }

}
