package com.apartments.base.owner.mapper;

import com.apartments.base.owner.models.EditOwnerDto;
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

    public static Owner toOwner(EditOwnerDto editOwnerDto) {
        Objects.requireNonNull(editOwnerDto, "Input cannot be null");

        Address address = Address.builder()
                .city(editOwnerDto.city())
                .street(editOwnerDto.street())
                .postcode(editOwnerDto.postcode())
                .build();

        return Owner.builder()
                .surname(editOwnerDto.surname())
                .lastname(editOwnerDto.lastname())
                .phoneNumber(editOwnerDto.phoneNumber())
                .address(address)
                .build();
    }
}
