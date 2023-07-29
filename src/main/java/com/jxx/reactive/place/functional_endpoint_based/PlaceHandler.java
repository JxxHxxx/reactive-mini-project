package com.jxx.reactive.place.functional_endpoint_based;

import com.jxx.reactive.place.annotation_based.application.PlaceService;
import com.jxx.reactive.place.annotation_based.domain.Address;
import com.jxx.reactive.place.annotation_based.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceHandler {

    private final PlaceService placeService;
    private final PlaceValidator validator;

    public Mono<ServerResponse> createPlace(ServerRequest request) {
        return request.bodyToMono(PlaceForm.class)
                .doOnNext(form -> validator.verify(form))
                .doOnNext(form -> placeService.enrollPlace(form))
                .map(form -> new ResponseBody<PlaceResponse>(201, "장소 등록 완료", new PlaceResponse(
                        form.id(), form.name(), Address.of(form.city(), form.district(), form.neighborhood(), form.streetNumber(), form.roomNumber())
                )))
                .flatMap(body -> ServerResponse
                        .created(URI.create("/v2/places/" + body.response().placeId()))
                        .body(Mono.just(body), PlaceResponse.class))

                .onErrorResume(IllegalArgumentException.class,
                        ex -> ServerResponse.badRequest()
                                .bodyValue(ErrorResponse.badRequest(ex.getMessage())));
    }

    public Mono<ServerResponse> findPlace(ServerRequest request) {
        Long placeId = Long.valueOf(request.pathVariable("place-id"));

        return placeService.findPlace(placeId)
                .map(response -> new ResponseBody<>(200, "장소 조회 완료", response))
                .flatMap(body -> ServerResponse.ok()
                        .body(Mono.just(body), ResponseBody.class))

                .onErrorResume(ServerWebInputException.class,
                        ex -> ServerResponse.badRequest()
                                .bodyValue(new ErrorResponse(400, List.of(ex.getReason()))
                                ));
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
                .flatMap(body -> ServerResponse.ok()
                        .body(Mono.just(new ResponseBody(200, "장소 수정 완료", body)), ResponseBody.class));
    }
}

