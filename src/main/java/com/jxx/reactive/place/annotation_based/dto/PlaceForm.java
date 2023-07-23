package com.jxx.reactive.place.annotation_based.dto;

import com.jxx.reactive.place.annotation_based.domain.Address;

public record PlaceForm(
        Long id,
        String name,
        String city,
        String district,
        String neighborhood,
        String streetNumber,
        String roomNumber
) {
    public static PlaceForm of(Long id, String name, Address address) {
        return new PlaceForm(id, name, address.getCity(), address.getDistrict(), address.getNeighborhood(), address.getStreetNumber(), address.getRoomNumber());
    }
}
