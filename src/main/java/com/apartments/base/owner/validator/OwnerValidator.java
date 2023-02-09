package com.apartments.base.owner.validator;

import com.apartments.base.owner.models.Owner;
import com.apartments.base.utils.validator.Validator;
import io.vavr.control.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OwnerValidator {
   private static final Pattern SURNAME_LASTNAME_PATTERN = Pattern.compile("^\\p{L}{1,29}$");
   private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{9}$");

    private final Owner owner;

    public OwnerValidator(Owner owner) {
        this.owner = owner;
    }

    public Validation<List<String>, Owner> validate() {
        List<String> invalidFields = new ArrayList<>();
        if (!validateName(owner.getSurname())) {
            invalidFields.add("Invalid surname");
        }
        if (!validateName(owner.getLastname())) {
            invalidFields.add("Invalid lastname");
        }
        if (!Validator.validateString(owner.getAddress().getCity())) {
            invalidFields.add("Invalid city");
        }
        if (!Validator.validateString(owner.getAddress().getPostcode())) {
            invalidFields.add("Invalid postcode");
        }
        if (!Validator.validateString(owner.getAddress().getStreet())) {
            invalidFields.add("Invalid street");
        }
        if (!validatePhoneNumber(owner.getPhoneNumber())) {
            invalidFields.add("Invalid phone number");
        }

        return invalidFields.isEmpty() ?
                Validation.valid(this.owner) :
                Validation.invalid(invalidFields);

    }

    private boolean validateName(String name) {
        return Validator.validateString(SURNAME_LASTNAME_PATTERN, name);
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return Validator.validateString(PHONE_NUMBER_PATTERN, phoneNumber);
    }
}
