package com.deliveryfood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliveryfood.core.storage.StorageProperties.TipoStorage;
import com.deliveryfood.domain.service.FotoStorageService;
import com.deliveryfood.infrastructure.service.storage.LocalFotoStorageService;
import com.deliveryfood.infrastructure.service.storage.S3FotoStorageService;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "deliveryfood.storage.tipo", havingValue = "s3")
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta()
        );

        return S3Client.builder()
                .region(Region.of(storageProperties.getS3().getRegiao()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService();
        }
    }

}
