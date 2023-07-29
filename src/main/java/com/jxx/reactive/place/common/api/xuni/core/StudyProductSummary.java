package com.jxx.reactive.place.common.api.xuni.core;

public record StudyProductSummary(
        String creator,
        String name,
        String firstChapterName
) {
    public static StudyProductSummary of(String creator, String name, String firstChapterName) {
        return new StudyProductSummary(creator, name, firstChapterName);
    }
}
