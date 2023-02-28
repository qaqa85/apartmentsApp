package com.apartments.base.owner.mapper;

import com.apartments.base.apartment.models.Apartment;
import com.apartments.base.apartment.models.dto.WithoutOwnersApartmentDto;
import com.apartments.base.apartment.models.mapper.ApartmentMapper;
import com.apartments.base.owner.models.dto.AllDetailsOwnerDto;
import com.apartments.base.owner.models.dto.EditOwnerDto;
import com.apartments.base.owner.models.dto.NewOwnerDto;
import com.apartments.base.owner.models.Owner;
import com.apartments.base.utils.model.Address;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    public static AllDetailsOwnerDto toAllDetailsOwnerDto(Owner owner) {
        Objects.requireNonNull(owner, "Input cannot be null");
        Objects.requireNonNull(owner.getAddress(), "Owner address cannot be null");

        return new AllDetailsOwnerDto(
                owner.getSurname(),
                owner.getLastname(),
                owner.getPhoneNumber(),
                owner.getAddress(),
                toWithoutOwnersApartmentDtosList(owner.getApartments())
        );
    }

    private static List<WithoutOwnersApartmentDto> toWithoutOwnersApartmentDtosList(Collection<Apartment> apartments) {
        if(Objects.isNull(apartments) || apartments.isEmpty()) {
            return Collections.emptyList();
        }

        return apartments.stream()
                .map(ApartmentMapper::toWithoutOwnersApartmentDto)
                .toList();
    }
}
