package com.jxx.reactive.place.annotation_based.application;

import com.jxx.reactive.place.annotation_based.domain.Address;
import com.jxx.reactive.place.annotation_based.domain.Place;
import com.jxx.reactive.place.annotation_based.domain.PlaceRepository;
import com.jxx.reactive.place.annotation_based.dto.PlaceForm;
import com.jxx.reactive.place.annotation_based.dto.PlaceResponse;
import com.jxx.reactive.place.annotation_based.dto.PlaceUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Mono<PlaceResponse> enrollPlace(PlaceForm form) {
        Place place = Place.builder()
                .id(form.id())
                .name(form.name())
                .address(Address.of(
                        form.city(),
                        form.district(),
                        form.neighborhood(),
                        form.streetNumber(),
                        form.roomNumber()))
                .build();

        placeRepository.save(place);
        return Mono.just(new PlaceResponse(place.getId(), place.getName(), place.getAddress()));
    }

    public Mono<PlaceResponse> findPlace(Long placeId) {

        Place place = placeRepository.findBy(placeId);
        if (place == null) {
            return Mono
                    .error(() -> new ServerWebInputException("존재하지 않은 장소입니다"));
        }
        return Mono
                .just(new PlaceResponse(place.getId(), place.getName(), place.getAddress()))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<List<PlaceResponse>> findAllPlace() {
        List<Place> places = placeRepository.findAll();

        return Mono.just(places.stream()
                .map(place -> new PlaceResponse(place.getId(), place.getName(), place.getAddress()))
                .toList());
    }

    public PlaceResponse updatePlace(PlaceUpdateForm form) {
        Place place = placeRepository.update(form.placeId(), form.placeName());

        return new PlaceResponse(place.getId(), place.getName(), place.getAddress());
    }
}
