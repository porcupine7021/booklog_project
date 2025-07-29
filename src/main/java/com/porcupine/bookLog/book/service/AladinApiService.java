package com.porcupine.bookLog.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.porcupine.bookLog.book.dto.AladinApiWrapper;
import com.porcupine.bookLog.book.dto.AladinBookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AladinApiService {

    private static final Logger log = LoggerFactory.getLogger(AladinApiService.class);

    private final WebClient webClient;

    @Value("${aladin.api.key}")
    private String apiKey;

    @Value("${aladin.api.endpoint}")
    private String apiEndpoint;

    public AladinApiService(WebClient aladinWebClient) {
        this.webClient = aladinWebClient;
    }

    public List<AladinBookResponse> searchBooks(String keyword) {
        try {
            // 이중 인코딩 방지: keyword를 그대로 사용
            String uri = UriComponentsBuilder
                    .fromUriString(apiEndpoint)
                    .queryParam("ttbkey", apiKey)
                    .queryParam("Query", keyword)
                    .queryParam("QueryType", "Keyword")
                    .queryParam("Output", "JS")
                    .queryParam("Version", "20131101")
                    .build()
                    .toUriString();

            log.info("요청 URI: {}", uri);

            // 응답 본문을 String으로 받아서 디버깅 후 파싱
            AladinApiWrapper wrapper = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,
                            clientResponse -> Mono.error(new RuntimeException("Aladin API 호출 실패")))
                    .bodyToMono(String.class)
                    .doOnNext(json -> log.info(" 원본 JSON 응답: {}", json))
                    .map(json -> {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            return mapper.readValue(json, AladinApiWrapper.class);
                        } catch (Exception e) {
                            log.error("JSON 파싱 실패", e);
                            return null;
                        }
                    })
                    .timeout(Duration.ofSeconds(5))
                    .block();

            return wrapper != null && wrapper.getItem() != null
                    ? Arrays.asList(wrapper.getItem())
                    : Collections.emptyList();

        } catch (Exception e) {
            log.error("도서 검색 중 예외 발생", e);
            return Collections.emptyList();
        }
    }
}
