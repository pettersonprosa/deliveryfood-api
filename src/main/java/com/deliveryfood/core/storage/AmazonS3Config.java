package com.deliveryfood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonS3Config {

    @Autowired
    private StorageProperties storageProperties;

    // @Bean
    // public AmazonS3 amazonS3() {
    //     var credentials = new BasicAWSCredentials(
    //             storageProperties.getS3().getIdChaveAcesso(),
    //             storageProperties.getS3().getChaveAcessoSecreta());

    //     AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
    //         storageProperties.getS3().getIdChaveAcesso(),
    //         storageProperties.getS3().getChaveAcessoSecreta()
    //     );

    //     return AmazonS3ClientBuilder.standard()
    //     .withCredentials(new AWSStaticCredentialsProvider(credentials))
    //     .withRegion(storageProperties.getS3().getRegiao())
    //     .build();
        
    // }

    @Bean
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

}
