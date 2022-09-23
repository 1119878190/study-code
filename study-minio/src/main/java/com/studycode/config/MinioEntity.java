package com.studycode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置
 *
 * @author terry
 * @version 1.0
 * @date 2022/8/21 21:01
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioEntity {


    private String endpoint;

    private String userName;

    private String password;

    private String bucketName;



}
