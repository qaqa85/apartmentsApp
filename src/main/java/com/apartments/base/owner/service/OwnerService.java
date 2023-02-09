package com.apartments.base.owner.service;

import com.apartments.base.owner.models.NewOwnerDto;
import io.vavr.control.Validation;

import java.util.List;
import java.util.UUID;

public interface OwnerService {
    Validation<List<String>, UUID> saveOwner(NewOwnerDto newOwnerDto);
}
