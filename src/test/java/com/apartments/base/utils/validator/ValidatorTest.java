package com.apartments.base.utils.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ValidatorTest {

    @ParameterizedTest(name = "should return {0} when {1} passed")
    @MethodSource(value = "uuidValidationTestSupplier")
    void shouldReturnCorrectResultWhenIdPassed(Boolean expected, String id) {
        // WHEN
        Boolean result = Validator.validateUUID(id);

        //THEN
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenNullPassed() {
        // WHEN + THEN
        assertThatThrownBy(() -> Validator.validateUUID(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageEndingWith("cannot be null");
    }

    private static Stream<Arguments> uuidValidationTestSupplier() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "invalidId"),
                Arguments.of(true, "8149d8b4-b1ed-11ed-afa1-0242ac120002")
        );
    }
}