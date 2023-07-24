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
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

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
