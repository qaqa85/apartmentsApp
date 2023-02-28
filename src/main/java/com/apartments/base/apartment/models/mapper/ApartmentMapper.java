package com.apartments.base.apartment.models.mapper;

import com.apartments.base.apartment.models.Apartment;
import com.apartments.base.apartment.models.dto.WithoutOwnersApartmentDto;

import java.util.Objects;

public class ApartmentMapper {
    public static WithoutOwnersApartmentDto toWithoutOwnersApartmentDto(Apartment apartment) {
        Objects.requireNonNull(apartment, "Input cannot be null");

        return new WithoutOwnersApartmentDto(
                apartment.getTitle(),
                apartment.getAddress(),
                apartment.getPrice(),
                apartment.getStatus(),
                apartment.getDescription()
        );
    }
}
