package com.apartments.base.apartment.models.mapper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ApartmentMapperTest {

    @Test
    void shouldThrowNullPointerExceptionWhenInputIsNull() {
        // WHEN + THEN
        assertThatThrownBy(() -> ApartmentMapper.toWithoutOwnersApartmentDto(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageEndingWith("cannot be null");
    }
}