package com.southwind.mmall002.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @Author: Lucas *
 * @Date: 2021/6/24 22:53 *
 */
@Configuration
public class ElasticsearchConfig {


    @Bean
    public RestHighLevelClient restHighLevelClient(){
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("8.129.175.148:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
