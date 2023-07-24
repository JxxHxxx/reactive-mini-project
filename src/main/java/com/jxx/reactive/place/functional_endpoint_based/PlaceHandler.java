package com.jxx.reactive.place.functional_endpoint_based;

import com.jxx.reactive.place.annotation_based.application.PlaceService;
import com.jxx.reactive.place.annotation_based.domain.Address;
import com.jxx.reactive.place.annotation_based.domain.Place;
import com.jxx.reactive.place.annotation_based.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component("placeHandlerV1")
@RequiredArgsConstructor
public class PlaceHandler {

    private final PlaceService placeService;

    public Mono<ServerResponse> createPlace(ServerRequest request) {
        return request.bodyToMono(PlaceForm.class)
                .doOnNext(form -> placeService.enrollPlace(form))
                .map(form -> new PlaceResponseBody<PlaceResponse>(
                        200,
                        "테스트",
                        new PlaceResponse(
                                form.id(),
                                form.name(),
                                Address.of(
                                        form.city(),
                                        form.district(),
                                        form.neighborhood(),
                                        form.streetNumber(),
                                        form.roomNumber()
                                )
                        )
                )).flatMap(body -> ServerResponse // 이하 응답을 만드는 과정
                        .created(URI.create("/v2/places/" + body.response().placeId()))
                        .body(Mono.just(body), PlaceResponse.class));
    }

    public Mono<ServerResponse> findPlace(ServerRequest request) {
        Long placeId = Long.valueOf(request.pathVariable("place-id"));
        Mono<PlaceResponseBody<PlaceResponse>> body = placeService.findPlace(placeId)
                .map(response -> new PlaceResponseBody<PlaceResponse>(200, "장소 조회 완료", response));

        return ServerResponse
                .ok()
                .body(body, PlaceResponseBody.class)
                .switchIfEmpty(ServerResponse.notFound().build()); // 만약 데이터가 없으면 이 모노로 fallback
    }

    public Mono<ServerResponse> findPlaces(ServerRequest request) {
        Mono<PlaceListResponseBody> body = placeService.findAllPlace()
                .map(response -> new PlaceListResponseBody(200, "장소들 조회 완료", response));

        return ServerResponse
                .ok()
                .body(body, PlaceListResponseBody.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updatePlace(ServerRequest request) {
        return request.bodyToMono(PlaceUpdateForm.class)
                .map(form -> placeService.updatePlace(form))
                .flatMap(body -> ServerResponse
                        .ok()
                        .body(Mono.just(new PlaceResponseBody<PlaceResponse>(200,"장소 수정 완료" ,body)), PlaceResponseBody.class));
    }
}

