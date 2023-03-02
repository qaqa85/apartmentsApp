package com.apartments.base.owner.models.dto;

import java.util.UUID;

public record SlimOwnerDto (
         UUID id,
         String surname,
         String lastname,
         String city
) {
}
