package com.o1jobs.crm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "r2")
public record R2Properties(
        String endpoint,
        String bucketName,
        String accessKeyId,
        String secretAccessKey
) {
}