package com.apartments.base.owner.service;

import com.apartments.base.owner.models.dto.AllDetailsOwnerDto;
import com.apartments.base.owner.models.dto.EditOwnerDto;
import com.apartments.base.owner.models.dto.NewOwnerDto;
import com.apartments.base.owner.models.dto.PageFilterSortOwnerDto;
import com.apartments.base.utils.model.ErrorMessage;
import io.vavr.control.Validation;

import java.util.UUID;

public interface OwnerService {
    Validation<ErrorMessage, UUID> saveOwner(NewOwnerDto newOwnerDto);

    Validation<ErrorMessage, UUID> editOwner(EditOwnerDto editOwnerDto, String id);

    Validation<ErrorMessage, AllDetailsOwnerDto> getOwner(String id);

    Object getOwners(PageFilterSortOwnerDto requirements);
}