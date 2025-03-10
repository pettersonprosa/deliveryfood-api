package com.deliveryfood;

import com.deliveryfood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class DeliveryfoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryfoodApiApplication.class, args);
    }

}
