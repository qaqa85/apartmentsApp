package com.apartments.base.owner.service;

import com.apartments.base.apartment.models.Apartment;
import com.apartments.base.owner.OwnerBuilderFactory;
import com.apartments.base.owner.models.Owner;
import com.apartments.base.owner.models.dto.EditOwnerDto;
import com.apartments.base.owner.models.dto.NewOwnerDto;
import com.apartments.base.owner.repository.OwnerRepository;
import com.apartments.base.utils.model.Address;
import com.apartments.base.utils.model.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {
    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerServiceImpl ownerService;

    @Test
    @DisplayName("should return valid object when input data is correct")
    void shouldReturnValidObjectWhenInputDataIsCorrect() {
        // GIVEN
        NewOwnerDto dto = new NewOwnerDto(
                OWNER.getSurname(),
                OWNER.getLastname(),
                OWNER.getPhoneNumber(),
                OWNER.getAddress().getCity(),
                OWNER.getAddress().getStreet(),
                OWNER.getAddress().getPostcode());

        given(ownerRepository.save(any(Owner.class))).willReturn(OWNER);

        // WHEN
        var result = ownerService.saveOwner(dto);

        // THEN
        Mockito.verify(ownerRepository).save(any(Owner.class));
        assertTrue(result.isValid());
        assertThat(result.get()).isEqualTo(OWNER.getId());
    }

    @Test
    @DisplayName("should throw nullPointerException when input dto is null")
    void SaveOwnerShouldThrowNullPointerExceptionWhenInputDtoIsNull() {
        // GIVEN
        NewOwnerDto dto = new NewOwnerDto(
                "",
                OWNER.getLastname(),
                OWNER.getPhoneNumber(),
                OWNER.getAddress().getCity(),
                OWNER.getAddress().getStreet(),
                OWNER.getAddress().getPostcode());

        // WHEN
        var result = ownerService.saveOwner(dto);

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError().errorMessages()).containsExactly("Invalid surname");
        assertThat(result.getError().errorType()).isEqualTo(ErrorType.BAD_REQUEST);
    }

    @Test
    @DisplayName("should return invalid surname when input surname is incorrect")
    void shouldReturnInvalidSurnameWhenInputSurnameIsIncorrect() {
        // WHEN + THEN
        assertThatThrownBy(() -> ownerService.saveOwner(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageEndingWith("is null");

    }

    @Test
    @DisplayName("should return owner not found when id is incorrect")
    void shouldReturnOwnerNotFoundWhenIdIsIncorrect() {
        // GIVEN
        EditOwnerDto dto = new EditOwnerDto(
                "TestSurname",
                "TestLastname",
                "123456789",
                "testCityName",
                "testStreetName",
                "testPostcode");

        String ownerId = OWNER.getId().toString();
        given(ownerRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // WHEN
        var result = ownerService.editOwner(dto, ownerId);

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError().errorMessages()).contains("Owner not found");
        assertThat(result.getError().errorType()).isEqualTo(ErrorType.NOT_FOUND);
    }

    @Test
    @DisplayName("should return invalid phone number when phone number is invalid")
    void shouldReturnInvalidPhoneNumberWhenPhoneNumberIsInvalid() {
        // GIVEN
        EditOwnerDto dto = new EditOwnerDto(
                "TestSurname",
                "TestLastname",
                "invalid phone number",
                "testCityName",
                "testStreetName",
                "testPostcode");

        String ownerId = OWNER.getId().toString();
        given(ownerRepository.findById(any(UUID.class))).willReturn(Optional.of(OWNER));

        // WHEN
        var result = ownerService.editOwner(dto, ownerId);

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError().errorMessages()).containsExactly("Invalid phone number");
        assertThat(result.getError().errorType()).isEqualTo(ErrorType.BAD_REQUEST);
    }

    @Test
    @DisplayName("should save edited owner when input is correct and owner exists in database")
    void shouldSaveEditedOwnerWhenInputDataIsCorrectAndOwnerExistsInDatabase() {
        // GIVEN
        EditOwnerDto dto = new EditOwnerDto(
                "TestSurname",
                "TestLastname",
                "123456789",
                "testCityName",
                "testStreetName",
                "testPostcode");

        String ownerId = OWNER.getId().toString();

        Owner editedOwner = Owner.builder()
                .id(UUID.fromString(ownerId))
                .surname(dto.surname())
                .lastname(dto.lastname())
                .phoneNumber(dto.phoneNumber())
                .address(
                        Address.builder()
                                .city(dto.city())
                                .street(dto.street())
                                .postcode(dto.postcode())
                                .build())
                .build();


        given(ownerRepository.findById(any(UUID.class))).willReturn(Optional.of(OWNER));
        given(ownerRepository.save(any(Owner.class))).willReturn(editedOwner);

        // WHEN
        var result = ownerService.editOwner(dto, ownerId);

        // THEN
        assertTrue(result.isValid());
        assertThat(result.get()).isEqualTo(UUID.fromString(ownerId));
        Mockito.verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    @DisplayName("should throw nullPointerException when input dto is null")
    void editOwnerShouldThrowNullPointerExceptionWhenInputDtoIsNull() {
        // GIVEN
        given(ownerRepository.findById(any(UUID.class))).willReturn(Optional.of(OWNER));

        // WHEN + THEN
        assertThatThrownBy(() -> ownerService.editOwner(null, OWNER.getId().toString()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageEndingWith("is null");

    }

    @Test
    @DisplayName("should return owner not found when uuid is invalid")
    void getOwnerReturnOwnerNotFoundWhenUUIDIsInvalid() {
        // WHEN
        var result = ownerService.getOwner("invalidId");

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError().errorMessages()).containsExactly("Owner not found");
        assertThat(result.getError().errorType()).isEqualTo(ErrorType.NOT_FOUND);
    }

    @Test
    @DisplayName("should return owner not found when owner is not found")
    void getOwnerShouldReturnOwnerNotFoundWhenOwnerIsNotFound() {
        // GIVEN
        given(ownerRepository.getOwnerEagerlyById(any(UUID.class)))
                .willReturn(Optional.empty());

        // WHEN
        var result = ownerService.getOwner(OWNER.getId().toString());

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError().errorMessages()).containsExactly("Owner not found");
        assertThat(result.getError().errorType()).isEqualTo(ErrorType.NOT_FOUND);
    }

    @Test
    @DisplayName("should return owner when owner exists")
    void getOwnerShouldReturnOwnerWhenOwnerExists() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .apartments(
                        List.of(Apartment.builder().id(UUID.randomUUID())
                                .title("testApartment").build())
                )
                .build();

        given(ownerRepository.getOwnerEagerlyById(any(UUID.class))).willReturn(Optional.of(owner));

        // WHEN
        var result = ownerService.getOwner(owner.getId().toString());

        // THEN
        assertTrue(result.isValid());
        assertThat(result.get().phoneNumber()).isEqualTo(owner.getPhoneNumber());
    }

    private final Owner OWNER = OwnerBuilderFactory
            .getDefaultOwnerBuilder()
            .build();
}