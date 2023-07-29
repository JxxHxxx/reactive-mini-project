package com.jxx.reactive.place.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.server.xuni.group}")
    private String XUNI_GROUP_URL;


    @Value("${api.server.kakao.map}")
    private String KAKAO_MAP_URL;

    @Bean
    WebClient xuniClient() {
        return WebClient.create(XUNI_GROUP_URL);
    }

    @Bean
    WebClient kakaoClient() {
        return WebClient.create(KAKAO_MAP_URL);
    }
}
