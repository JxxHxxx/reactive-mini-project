package com.jxx.reactive.place.annotation_based.dto;

public record PlaceResponseBody<T>(
        Integer code,
        String message,
        T response
) {
}
