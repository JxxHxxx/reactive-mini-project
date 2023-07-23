package com.jxx.reactive.place.annotation_based.dto;

import java.util.List;

public record PlaceListResponseBody<T>(
        Integer code,
        String message,
        List<T> response
) {
}
