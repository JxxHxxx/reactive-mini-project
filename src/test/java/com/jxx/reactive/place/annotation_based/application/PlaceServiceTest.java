package com.jxx.reactive.place.annotation_based.application;

import com.jxx.reactive.place.annotation_based.domain.Address;
import com.jxx.reactive.place.annotation_based.domain.PlaceRepository;
import com.jxx.reactive.place.annotation_based.dto.PlaceForm;
import com.jxx.reactive.place.annotation_based.dto.PlaceResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
class PlaceServiceTest {

    @Autowired
    PlaceService placeService;
    @Autowired
    PlaceRepository placeRepository;

    /** StepVerifier 내부에서 address 를 참조할 때 참조 값이 달라짐 이유는 모르겠음
     * 그래서 equals&hashcode 재정의함
     */

    @BeforeEach
    void beforeEach() {
        placeRepository.deleteAll();
    }

    @Test
    void enroll_place() {
        Address address = Address.of("서울시", "송파구", "방이동", "05635", "202호");
        PlaceForm placeForm = PlaceForm.of(
                1l,
                "xuni 방이점",
                address);

        StepVerifier
                .create(placeService.enrollPlace(placeForm).log())
                .expectNext(new PlaceResponse(1l, "xuni 방이점", address))
                .expectComplete()
                .verify();
    }

    @Test
    void find() {
        Address address = Address.of("서울시", "송파구", "방이동", "05635", "202호");
        PlaceForm placeForm = PlaceForm.of(
                1l,
                "xuni 방이점",
                address);

        placeService.enrollPlace(placeForm);

        StepVerifier
                .create(placeService.findPlace(1l).log())
                .expectNext(new PlaceResponse(1l, "xuni 방이점", address))
                .expectComplete()
                .verify();

    }

}