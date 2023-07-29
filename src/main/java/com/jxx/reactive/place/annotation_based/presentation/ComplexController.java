package com.jxx.reactive.place.annotation_based.presentation;

import com.jxx.reactive.place.annotation_based.application.StudyProductExtractor;
import com.jxx.reactive.place.annotation_based.dto.ResponseBody;
import com.jxx.reactive.place.common.api.xuni.core.Content;
import com.jxx.reactive.place.common.api.xuni.core.ProductDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
public class ComplexController {
    private final StudyProductExtractor extractor;
    private final WebClient webClient;

    public ComplexController(StudyProductExtractor studyProductExtractor, @Qualifier("xuniClient") WebClient webClient) {
        this.extractor = studyProductExtractor;
        this.webClient = webClient;
    }

    @GetMapping("/v2/places")
    public ResponseEntity<Mono<String>> responseOnlyBody(@RequestParam String studyProductId) {
        Mono<String> response = webClient
                .get()
                .uri("/study-products/" + studyProductId).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);

        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping("/v3/places")
    public void responseHttp(@RequestParam String studyProductId) {
        Mono<ResponseEntity<String>> responseV2 = webClient
                .get()
                .uri("/study-products/" + studyProductId).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        responseV2.doOnNext(res -> log.info("# res : {}", res))
                .subscribe();
    }

    @GetMapping("/v4/places")
    public ResponseEntity<Mono<ResponseBody>> responseMappingToObject(@RequestParam String studyProductId) {
        Mono<ResponseBody> body = webClient
                .get()
                .uri("/study-products/" + studyProductId).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseBody.class);

        return ResponseEntity.status(OK).body(body);
    }

    @GetMapping("/v5/places")
    public ResponseEntity<Mono<List<Content>>> summary(@RequestParam String studyProductId) {
        ParameterizedTypeReference<ResponseBody<ProductDetail>> responseType = new ParameterizedTypeReference<>(){};

        Mono<ResponseBody<ProductDetail>> responseBody = webClient
                .get()
                .uri("/study-products/" + studyProductId).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType);

        Mono<List<Content>> contents = responseBody.map(body -> extractor.extractContents(body));

        return ResponseEntity.status(OK).body(contents);
    }
}
