package com.apartments.base.owner.mapper;

import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.models.Owner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class OwnerMapperTest {
    @Test
    void shouldReturnOwnerObjectBasedOnDTO() {
        // GIVEN
        NewOwnerDto dto = new NewOwnerDto(
                "Damian",
                "Tanski",
                "999999999",
                "Gdansk",
                "xxx",
                "10999");

        // WHEN
        Owner owner = OwnerMapper.toOwner(dto);

        // THEN
        assertNull(owner.getId());
        assertEquals(dto.surname(), owner.getSurname());
        assertEquals(dto.lastname(), owner.getLastname());
        assertEquals(dto.phoneNumber(), owner.getPhoneNumber());
        assertEquals(dto.city(), owner.getAddress().getCity());
        assertEquals(dto.street(), owner.getAddress().getStreet());
        assertEquals(dto.postcode(), owner.getAddress().getPostcode());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenInputIsNull() {
        // WHEN + THEN
        assertThatThrownBy(() -> OwnerMapper.toOwner(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input cannot be null");
    }
}