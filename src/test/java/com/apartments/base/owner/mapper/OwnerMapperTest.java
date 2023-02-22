package com.apartments.base.owner.mapper;

import com.apartments.base.owner.models.EditOwnerDto;
import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.models.Owner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class OwnerMapperTest {
    @Test
    @DisplayName("should return owner object based on NewOwnerDto")
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
    @DisplayName("should return owner object based on EditOwnerDto")
    void shouldReturnOwnerObjectBasedOnEditOwnerDto() {
        // GIVEN
        EditOwnerDto dto = new EditOwnerDto(
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
    @DisplayName("should throw null pointer exception when input is null")
    void shouldThrowNullPointerExceptionWhenInputIsNull() {
        // GIVEN
        EditOwnerDto editOwnerDto = null;

        // WHEN + THEN
        assertThatThrownBy(() -> OwnerMapper.toOwner(editOwnerDto))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input cannot be null");
    }
}