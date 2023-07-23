package com.jxx.reactive.place.annotation_based.dto;

import com.jxx.reactive.place.annotation_based.domain.Address;

public record PlaceResponse(
        Long placeId,
        String placeName,
        Address placeAddress
) {
}
