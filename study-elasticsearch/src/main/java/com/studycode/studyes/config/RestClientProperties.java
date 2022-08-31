package com.studycode.studyes.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "elasticsearch.data.source")
@Component
@Data
public class RestClientProperties {

    /**
     * 这里可以配置多个地址，在配置文件里面可以有：spring.elasticsearch.rest.uris=http://192.168.164.135:9200
     */
    private List<String> uris = new ArrayList<>(Collections.singletonList("http://localhost:9200"));

    /**
     * 连接用户名.
     */
    private String username;

    /**
     * 连接密码.
     */
    private String password;


}