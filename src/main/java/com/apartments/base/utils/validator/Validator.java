package com.apartments.base.utils.validator;

import java.util.regex.Pattern;

public class Validator {
    private Validator() {

    }

    public static boolean validateString(Pattern pattern, String string) {
        return string != null && pattern.matcher(string).matches();
    }

    public static boolean validateString(String string) {
        return string != null && !string.isBlank();
    }
}
