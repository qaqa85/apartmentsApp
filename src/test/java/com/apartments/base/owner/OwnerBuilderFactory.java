package com.apartments.base.owner;

import com.apartments.base.owner.models.Owner;
import com.apartments.base.utils.model.Address;

import java.util.UUID;

public class OwnerBuilderFactory {
    public static Owner.OwnerBuilder getDefaultOwnerBuilder() {
        return Owner.builder()
                .id(UUID.fromString("ec0c0b08-a6f4-11ed-afa1-0242ac120002"))
                .surname("Damian")
                .lastname("Tański")
                .phoneNumber("999999999")
                .address(getDefaultAddressBuilder().build());
    }

    public static Address.AddressBuilder getDefaultAddressBuilder() {
        return Address.builder()
                .city("Gdańsk")
                .street("xxx")
                .postcode("yyy");
    }
}
