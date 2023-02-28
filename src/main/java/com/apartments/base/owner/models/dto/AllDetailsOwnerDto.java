package com.apartments.base.owner.models.dto;

import com.apartments.base.apartment.models.dto.WithoutOwnersApartmentDto;
import com.apartments.base.utils.model.Address;
import lombok.Builder;

import java.util.List;

@Builder
public record AllDetailsOwnerDto(
        String surname,
        String lastname,
        String phoneNumber,
        Address address,
        List<WithoutOwnersApartmentDto> apartments
) {
}

