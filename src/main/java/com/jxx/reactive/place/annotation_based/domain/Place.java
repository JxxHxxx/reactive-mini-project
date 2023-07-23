package com.jxx.reactive.place.annotation_based.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Place {

    private Long id;
    private String name;
    private Address address;

    @Builder
    public Place(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
