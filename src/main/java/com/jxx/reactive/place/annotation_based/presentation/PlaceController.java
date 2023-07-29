package com.jxx.reactive.place.annotation_based.presentation;

import com.jxx.reactive.place.annotation_based.application.PlaceService;
import com.jxx.reactive.place.annotation_based.dto.PlaceForm;
import com.jxx.reactive.place.annotation_based.dto.PlaceListResponseBody;
import com.jxx.reactive.place.annotation_based.dto.PlaceResponse;
import com.jxx.reactive.place.annotation_based.dto.PlaceResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/places")
    public ResponseEntity<Mono<PlaceResponseBody>> createPlace(@RequestBody PlaceForm placeForm) {
        Mono<PlaceResponse> responseMono = placeService.enrollPlace(placeForm);

        Mono<PlaceResponseBody> body = responseMono
                .map(response -> new PlaceResponseBody(201, "장소 등록 완료", response));

        return ResponseEntity.ok(body);
    }

    @GetMapping("/places/{place-id}")
    public ResponseEntity<Mono<PlaceResponseBody>> findPlace(@PathVariable("place-id") Long placeId) {
        Mono<PlaceResponse> placeResponse = placeService.findPlace(placeId);

        Mono<PlaceResponseBody> body = placeResponse
                .map(response -> new PlaceResponseBody(200, "장소 조회 완료", response));

        return ResponseEntity.ok(body);
    }

    @GetMapping("/places")
    public ResponseEntity<Mono<PlaceListResponseBody>> findAllPlace() {
        Mono<List<PlaceResponse>> placeResponses = placeService.findAllPlace();

        Mono<PlaceListResponseBody> body = placeResponses.map(response ->
                new PlaceListResponseBody<PlaceResponse>(200, "장소 전체 조회 완료", response));

        return ResponseEntity.ok(body);
    }
}
