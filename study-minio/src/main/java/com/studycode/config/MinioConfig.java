package com.studycode.config;

import com.studycode.util.MinioUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lx
 * @Date: 2022/09/23
 * @Description:
 */
@Configuration
public class MinioConfig {


    @Bean
    public MinioUtil minioClient( MinioEntity minioEntity) {
        return new MinioUtil(minioEntity.getEndpoint(), minioEntity.getBucketName(), minioEntity.getUserName(), minioEntity.getPassword());
    }
}
