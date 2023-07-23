package com.jxx.reactive.place.annotation_based.domain;

import java.util.List;

public interface PlaceRepository {
    Place save(Place place);

    public List<Place> findAll();

    public Place findBy(Long id);

    void deleteAll();
}
