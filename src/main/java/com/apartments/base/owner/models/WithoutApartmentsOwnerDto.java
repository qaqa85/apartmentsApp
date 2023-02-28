package com.apartments.base.owner.models;

import com.apartments.base.utils.model.Address;

public record WithoutApartmentsOwnerDto(
        String surname,
        String lastname,
        String phoneNumber,
        Address address
) {
}
