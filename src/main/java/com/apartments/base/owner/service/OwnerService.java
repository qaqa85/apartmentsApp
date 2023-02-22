package com.apartments.base.owner.service;

import com.apartments.base.owner.models.EditOwnerDto;
import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.utils.models.ErrorMessage;
import io.vavr.control.Validation;

import java.util.UUID;

public interface OwnerService {
    Validation<ErrorMessage, UUID> saveOwner(NewOwnerDto newOwnerDto);

    Validation<ErrorMessage, UUID> editOwner(EditOwnerDto editOwnerDto, String id);
}
