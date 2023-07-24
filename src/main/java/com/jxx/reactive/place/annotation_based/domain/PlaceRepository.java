package com.jxx.reactive.place.annotation_based.domain;

import java.util.List;

public interface PlaceRepository {
    Place save(Place place);

    List<Place> findAll();

    Place findBy(Long id);

    Place update(Long id, String name);

    void deleteAll();
}
