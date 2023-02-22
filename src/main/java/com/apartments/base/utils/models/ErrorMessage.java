package com.apartments.base.utils.models;

import java.util.Collection;

public record ErrorMessage (
        Collection<String> errorMessages,
        ErrorType errorType
) {
}
