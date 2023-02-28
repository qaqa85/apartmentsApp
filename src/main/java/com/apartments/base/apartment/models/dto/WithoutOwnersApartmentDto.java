package com.apartments.base.apartment.models.dto;

import com.apartments.base.apartment.models.Status;
import com.apartments.base.utils.model.Address;

import java.math.BigDecimal;

public record WithoutOwnersApartmentDto(String title,
                                        Address address,
                                        BigDecimal price,
                                        Status status,
                                        String description) {

}