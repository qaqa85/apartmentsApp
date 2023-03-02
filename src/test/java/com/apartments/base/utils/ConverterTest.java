package com.apartments.base.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @ParameterizedTest(name = "Should return empty optional when input is \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {"12.3", "21,3", "12a3", "a" , "b321", "b:3", "12 32", "--123"})
    void shouldReturnEmptyOptionalWhenInputIsNotNumber(String notNumber) {
        // WHEN
        var result = Converter.toInt(notNumber);

        // THEN
        assertThat(result).isEmpty();
    }

    @ParameterizedTest(name = "Should return Optional with number when input is \"{0}\"")
    @ValueSource(strings = {"12", "1", "123451", "21", "-12"})
    void shouldReturnOptionalWithNumberWhenInputIsANumber(String number) {
        // WHEN
        var result = Converter.toInt(number);

        // THEN
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(Integer.parseInt(number));
    }

}