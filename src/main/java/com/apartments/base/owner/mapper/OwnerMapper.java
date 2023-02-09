package com.apartments.base.owner.mapper;

import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.models.Owner;
import com.apartments.base.utils.models.Address;

import java.util.Objects;

public class OwnerMapper {
    private OwnerMapper() {

    }

    public static Owner toOwner(NewOwnerDto newOwnerDto) {
        Objects.requireNonNull(newOwnerDto, "Input cannot be null");

        Address address = Address.builder()
                .city(newOwnerDto.city())
                .street(newOwnerDto.street())
                .postcode(newOwnerDto.postcode())
                .build();

        return Owner.builder()
                .surname(newOwnerDto.surname())
                .lastname(newOwnerDto.lastname())
                .phoneNumber(newOwnerDto.phoneNumber())
                .address(address)
                .build();
    }
}
