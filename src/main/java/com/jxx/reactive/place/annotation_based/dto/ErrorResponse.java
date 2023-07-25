package com.jxx.reactive.place.annotation_based.dto;

import java.util.List;

public record ErrorResponse(
        Integer code,
        List<String> message
) {
    public static ErrorResponse badRequest(String... message) {
        return new ErrorResponse(400, List.of(message));
    }
}
