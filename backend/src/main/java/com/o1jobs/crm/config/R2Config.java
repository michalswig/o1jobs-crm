package com.o1jobs.crm.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(R2Properties.class)
public class R2Config {

    @Bean
    public S3Client s3Client(R2Properties properties) {
        // R2 nie używa regionów AWS - Cloudflare rekomenduje stały string "auto"
        // przy integracji z SDK-ami napisanymi pod AWS S3.
        return S3Client.builder()
                .endpointOverride(URI.create(properties.endpoint()))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(properties.accessKeyId(), properties.secretAccessKey())
                ))
                .forcePathStyle(true)
                .build();
    }
}