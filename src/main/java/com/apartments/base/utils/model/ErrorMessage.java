package com.apartments.base.utils.model;

import java.util.*;

public record ErrorMessage (
        Collection<String> errorMessages,
        ErrorType errorType
) {
    public ErrorMessage (ErrorType errorType, String... errorMessage) {
       this(Arrays.asList(errorMessage), errorType);
    }
}
