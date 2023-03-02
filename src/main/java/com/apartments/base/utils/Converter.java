package com.apartments.base.utils;

import com.apartments.base.utils.validator.Validator;

import java.util.Optional;

public class Converter {
    public static Optional<Integer> toInt(String string) {
        return Optional.ofNullable(string)
                .filter(Validator::validateInteger)
                .map(Integer::parseInt);
    }
}
