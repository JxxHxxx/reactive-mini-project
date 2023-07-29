package com.jxx.reactive.place.annotation_based.dto;

import com.jxx.reactive.place.common.api.xuni.core.Content;

public record ResponseBody<T>(
        Integer status,
        String message,
        T response
) {}
