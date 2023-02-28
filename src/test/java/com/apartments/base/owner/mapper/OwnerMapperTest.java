package com.apartments.base.owner.mapper;

import com.apartments.base.apartment.models.Apartment;
import com.apartments.base.owner.OwnerBuilderFactory;
import com.apartments.base.owner.models.Owner;
import com.apartments.base.owner.models.dto.EditOwnerDto;
import com.apartments.base.owner.models.dto.NewOwnerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class OwnerMapperTest {
    @Test
    @DisplayName("should return owner object based on NewOwnerDto")
    void toOwnerShouldReturnOwnerObjectBasedOnDTO() {
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
    void toOwnerShouldReturnOwnerObjectBasedOnEditOwnerDto() {
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
    @DisplayName("should throw nullPointerException when input is null")
    void toOwnerShouldThrowNullPointerExceptionWhenInputEditDtoIsNull() {
        // GIVEN
        EditOwnerDto editOwnerDto = null;

        // WHEN + THEN
        assertThatThrownBy(() -> OwnerMapper.toOwner(editOwnerDto))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input cannot be null");
    }

    @Test
    @DisplayName("should throw nullPointerException when input is null")
    void toOwnerShouldThrowNullPointerExceptionWhenInputNewDtoIsNull() {
        // GIVEN
        NewOwnerDto newOwnerDto = null;

        // WHEN + THEN
        assertThatThrownBy(() -> OwnerMapper.toOwner(newOwnerDto))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input cannot be null");
    }

    @Test
    @DisplayName("should throw nullPointerException")
    void toAllDetailsOwnerDtoShouldThrowNullPointerExceptionWhenInputIsNull() {
        // WHEN + THEN
        assertThatThrownBy(() -> OwnerMapper.toAllDetailsOwnerDto(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Input cannot be null");
    }

    @MethodSource("apartmentListEmptyOrNull")
    @ParameterizedTest(name = "should return allDetailsDto with empty apartment list when apartment input is {1}")
    void toAllDetailsOwnerDtoShouldReturnEmptyApartmentsListWhenApartmentsAreNull(List<Apartment> apartments) {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .apartments(apartments)
                .build();

        // WHEN
        var result = OwnerMapper.toAllDetailsOwnerDto(owner);


        // THEN
        assertEquals(result.surname(), owner.getSurname());
        assertEquals(result.lastname(), owner.getLastname());
        assertEquals(result.phoneNumber(), owner.getPhoneNumber());
        assertEquals(result.address().getCity(), owner.getAddress().getCity());
        assertEquals(result.address().getStreet(), owner.getAddress().getStreet());
        assertEquals(result.address().getPostcode(), owner.getAddress().getPostcode());
        assertThat(result.apartments()).isEmpty();
    }

    @Test
    @DisplayName("should return allDetailsOwnerDto with single apartment")
    void toAllDetailsOwnerDtoShouldReturnAllDetailsOwnerDtoWithSingleApartment() {
        // GIVEN
        Apartment apartment = Apartment.builder()
                .title("testApartment")
                .build();

        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .apartments(List.of(apartment))
                .build();

        // WHEN
        var result = OwnerMapper.toAllDetailsOwnerDto(owner);

        // THEN
        assertEquals(result.surname(), owner.getSurname());
        assertEquals(result.lastname(), owner.getLastname());
        assertEquals(result.phoneNumber(), owner.getPhoneNumber());
        assertEquals(result.address().getCity(), owner.getAddress().getCity());
        assertEquals(result.address().getStreet(), owner.getAddress().getStreet());
        assertEquals(result.address().getPostcode(), owner.getAddress().getPostcode());
        assertThat(result.apartments()).isNotEmpty();
    }

    private static Stream<List<Apartment>> apartmentListEmptyOrNull() {
        return Stream.of(Collections.emptyList(), null);
    }
}