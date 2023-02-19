package com.apartments.base.owner.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record NewOwnerDto(
        @Schema(example = "Adrian",
                type = "string",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 30,
                minLength = 1
        ) String surname,

        @Schema(example = "Kowalski",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 30,
                minLength = 1)
        String lastname,

        @Schema(example = "999999999",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 9,
                minLength = 9)
        String phoneNumber,
        @Schema(example = "London",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String city,

        @Schema(example = "Avenue",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String street,

        @Schema(example = "123",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String postcode) {
}
