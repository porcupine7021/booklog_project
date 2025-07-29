package com.porcupine.bookLog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    /*
    @Bean
    public WebClient aladinWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://www.aladin.co.kr")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }*/
    @Bean
    public WebClient aladinWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://www.aladin.co.kr") // ✅ 이게 정확한 주소입니다
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
