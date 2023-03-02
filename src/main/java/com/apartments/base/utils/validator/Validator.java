package com.apartments.base.utils.validator;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validator {
    public static Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    public static Pattern INTEGER_PATTERN = Pattern.compile("^-?(\\d)+$");

    private Validator() {}

    public static boolean validateString(Pattern pattern, String string) {
        return string != null && pattern.matcher(string).matches();
    }

    public static boolean validateUUID(String string) {
        Objects.requireNonNull(string, "uuid cannot be null");

        return UUID_PATTERN.matcher(string).matches();
    }

    public static boolean validateInteger(String string) {
        Objects.requireNonNull(string, "integer cannot be null");

        return INTEGER_PATTERN.matcher(string).matches();
    }

    public static boolean validateString(String string) {
        return string != null && !string.isBlank();
    }
}
