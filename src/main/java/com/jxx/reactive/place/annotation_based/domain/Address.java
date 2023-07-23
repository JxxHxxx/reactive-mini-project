package com.jxx.reactive.place.annotation_based.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Address {
    private String city;
    private String district;
    private String neighborhood;
    private String streetNumber;
    private String roomNumber;

    public Address(String city, String district, String neighborhood, String streetNumber, String roomNumber) {
        this.city = city;
        this.district = district;
        this.neighborhood = neighborhood;
        this.streetNumber = streetNumber;
        this.roomNumber = roomNumber;
    }

    public static Address of(String city, String district, String neighborhood, String streetNumber, String roomNumber) {
        return new Address(city, district, neighborhood, streetNumber, roomNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(district, address.district) &&
                Objects.equals(neighborhood, address.neighborhood) && streetNumber.equals(address.streetNumber) && roomNumber.equals(address.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, district, neighborhood, streetNumber, roomNumber);
    }
}
