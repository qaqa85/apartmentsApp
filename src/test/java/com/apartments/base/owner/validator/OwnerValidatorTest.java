package com.apartments.base.owner.validator;

import com.apartments.base.owner.OwnerBuilderFactory;
import com.apartments.base.owner.models.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OwnerValidatorTest {

    @Test
    @DisplayName("Should return invalid object when surname is longer than 30")
    void shouldReturnInvalidObjectWhenSurnameIsLongerThan30() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .surname("Drek vor Koalsmewosla solamsmaso")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid surname");
    }

    @Test
    @DisplayName("Should return invalid object when surname is empty")
    void shouldReturnInvalidObjectWhenSurnameIsEmpty() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .surname("")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid surname");
    }

    @Test
    @DisplayName("Should return invalid object when lastname string is longer than 30")
    void shouldReturnInvalidObjectWhenLastNameStringIsLongerThan30() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .lastname("Drek vor Koalsmewosla solamsmaso")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid lastname");
    }

    @Test
    @DisplayName("Should return invalid object when name string is empty")
    void shouldReturnInvalidObjectWhenLastnameStringIsEmpty() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .surname("")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid surname");
    }

    @Test
    @DisplayName("Should return invalid phone number when length is higher than 9")
    void shouldReturnFalseWhenLengthIsHigherThan9() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .phoneNumber("1234567890")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid phone number");
    }

    @Test
    @DisplayName("Should return invalid phone number when length is shorter than 9")
    void shouldReturnInvalidPhoneNumberWhenLengthIsShorterThan9() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .phoneNumber("12390")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid phone number");
    }

    @Test
    @DisplayName("Should return invalid city name when city is empty")
    void shouldReturnInvalidCityNameWhenCityIsEmpty() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .address(OwnerBuilderFactory.getDefaultAddressBuilder()
                        .city("")
                        .build())
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid city");
    }

    @Test
    @DisplayName("Should return invalid street name when street is empty")
    void shouldReturnInvalidStreetNameWhenStreetIsEmpty() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .address(OwnerBuilderFactory.getDefaultAddressBuilder()
                        .street("")
                        .build())
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid street");
    }

    @Test
    @DisplayName("Should return invalid postcode when postcode is empty")
    void shouldReturnInvalidPostcodeNameWhenPostcodeIsEmpty() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .address(OwnerBuilderFactory.getDefaultAddressBuilder()
                        .postcode("")
                        .build())
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isInvalid());
        assertThat(result.getError()).containsExactly("Invalid postcode");
    }

    @Test
    @DisplayName("Should return valid owner object when all fields are correct")
    void shouldReturnValidOwnerObjectWhenAllFieldsAreCorrect() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder()
                .phoneNumber("123456789")
                .build();

        // WHEN
        var result = new OwnerValidator(owner).validate();

        // THEN
        assertTrue(result.isValid());
        assertThat(result.get().getId()).isEqualTo(owner.getId());
    }
}