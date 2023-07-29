package com.jxx.reactive.place.common.api.xuni.core;

import java.util.List;

public record ProductDetail(
        String name,
        String category,
        String creator,
        String thumbnail,
        List<Content> contents
) {
}
