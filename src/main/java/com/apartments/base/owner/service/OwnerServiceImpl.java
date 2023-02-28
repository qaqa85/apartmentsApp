package com.apartments.base.owner.service;

import com.apartments.base.owner.mapper.OwnerMapper;
import com.apartments.base.owner.models.*;
import com.apartments.base.owner.models.dto.AllDetailsOwnerDto;
import com.apartments.base.owner.models.dto.EditOwnerDto;
import com.apartments.base.owner.models.dto.NewOwnerDto;
import com.apartments.base.owner.repository.OwnerRepository;
import com.apartments.base.owner.validator.OwnerValidator;
import com.apartments.base.utils.model.ErrorMessage;
import com.apartments.base.utils.model.ErrorType;
import com.apartments.base.utils.validator.Validator;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public Validation<ErrorMessage, UUID> saveOwner(NewOwnerDto newOwnerDto) {
        Validation<List<String>, Owner> owner = validateOwner(newOwnerDto);

        if (owner.isInvalid()) {
            return Validation.invalid(new ErrorMessage(owner.getError(), ErrorType.BAD_REQUEST));
        } else {
            return Validation.valid(ownerRepository.save(owner.get()).getId());
        }
    }

    @Override
    @Transactional
    public Validation<ErrorMessage, UUID> editOwner(EditOwnerDto editOwnerDto, String id) {
        Optional<Owner> owner = getSingleOwner(id);

        if (owner.isEmpty()) {
            return Validation.invalid(new ErrorMessage(List.of("Owner not found"), ErrorType.NOT_FOUND));
        }

        Validation<List<String>, Owner> validatedDto = validateOwner(editOwnerDto);
        if (validatedDto.isInvalid()) {
            return Validation.invalid(new ErrorMessage(validatedDto.getError(), ErrorType.BAD_REQUEST));
        }
        Owner editedOwner = validatedDto.get();
        editedOwner.setId(owner.get().getId());
        return Validation.valid(ownerRepository.save(editedOwner).getId());
    }

    @Override
    public Validation<ErrorMessage, AllDetailsOwnerDto> getOwner(String id) {
        return getSingleOwnerEagerly(id)
                .<Validation<ErrorMessage, AllDetailsOwnerDto>>map(Validation::valid)
                .orElseGet(() ->
                        Validation.invalid(new ErrorMessage(ErrorType.NOT_FOUND, "Owner not found")));
    }

    private Validation<List<String>, Owner> validateOwner(EditOwnerDto editOwnerDto) {
        return Optional.ofNullable(editOwnerDto)
                .map(OwnerMapper::toOwner)
                .map(this::checkOwner)
                .orElseThrow(() -> new NullPointerException("Given dto is null"));
    }

    private Validation<List<String>, Owner> validateOwner(NewOwnerDto newOwnerDto) {
        return Optional.ofNullable(newOwnerDto)
                .map(OwnerMapper::toOwner)
                .map(this::checkOwner)
                .orElseThrow(() -> new NullPointerException("Given dto is null"));
    }

    private Optional<Owner> getSingleOwner(String id) {
        return checkUUID(id)
                .flatMap(ownerRepository::findById);
    }

    private Optional<AllDetailsOwnerDto> getSingleOwnerEagerly(String id) {
        return checkUUID(id)
                .flatMap(ownerRepository::getOwnerEagerlyById)
                .map(OwnerMapper::toAllDetailsOwnerDto);
    }

    private Optional<UUID> checkUUID(String id) {
        return Optional.ofNullable(id)
                .filter(Validator::validateUUID)
                .map(UUID::fromString);
    }


    private Validation<List<String>, Owner> checkOwner(Owner owner) {
        return new OwnerValidator(owner).validate();
    }
}
