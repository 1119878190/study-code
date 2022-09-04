package com.studycode.studyes.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 这个是加载配置信息的类
public class RestClientConfigurations {

    @Configuration
    static class RestClientBuilderConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RestClientBuilder elasticsearchRestClientBuilder(RestClientProperties properties,
                                                                ObjectProvider<RestClientBuilderCustomizer> builderCustomizers) {
            HttpHost[] hosts = properties.getUris().stream().map(HttpHost::create).toArray(HttpHost[]::new);
            RestClientBuilder builder = RestClient.builder(hosts);
            PropertyMapper map = PropertyMapper.get();

            map.from(properties.getUsername()).whenHasText().to((username) -> {
                // 用户名不为空时
                // 设置密码
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                Credentials credentials = new UsernamePasswordCredentials(properties.getUsername(),
                        properties.getPassword());
                credentialsProvider.setCredentials(AuthScope.ANY, credentials);
                // 异步连接数配置
                builder.setHttpClientConfigCallback(
                        (httpClientBuilder) -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
            });
            builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
            return builder;
        }
    }

    @Configuration
    // 如果不引入elasticsearch-rest-high-level-client这个包，那么这个bean是不会被加载的
    @ConditionalOnClass(RestHighLevelClient.class)
    static class RestHighLevelClientConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RestHighLevelClient elasticsearchRestHighLevelClient(RestClientBuilder restClientBuilder) {
            return new RestHighLevelClient(restClientBuilder);
        }

        @Bean
        @ConditionalOnMissingBean
        public RestClient elasticsearchRestClient(RestClientBuilder builder,
                ObjectProvider<RestHighLevelClient> restHighLevelClient) {
            RestHighLevelClient client = restHighLevelClient.getIfUnique();
            if (client != null) {
                return client.getLowLevelClient();
            }
            return builder.build();
        }

    }

    @Configuration
    static class RestClientFallbackConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RestClient elasticsearchRestClient(RestClientBuilder builder) {
            return builder.build();
        }

    }

}