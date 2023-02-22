package com.apartments.base.owner.service;

import com.apartments.base.owner.mapper.OwnerMapper;
import com.apartments.base.owner.models.EditOwnerDto;
import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.models.Owner;
import com.apartments.base.owner.repository.OwnerRepository;
import com.apartments.base.owner.validator.OwnerValidator;
import com.apartments.base.utils.models.ErrorMessage;
import com.apartments.base.utils.models.ErrorType;
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
        Validation<List<String>, Owner> owner = Optional.ofNullable(newOwnerDto)
                .map(OwnerMapper::toOwner)
                .map(this::checkOwner)
                .orElseThrow(() -> new NullPointerException("Given dto is null"));

        if (owner.isInvalid()) {
            return Validation.invalid(new ErrorMessage(owner.getError(), ErrorType.BAD_REQUEST));
        } else {
            return Validation.valid(ownerRepository.save(owner.get()).getId());
        }
    }

    @Override
    @Transactional
    public Validation<ErrorMessage, UUID> editOwner(EditOwnerDto editOwnerDto, String id) {
        Optional<Owner> owner = getOwner(id);

        if (owner.isEmpty()) {
            return Validation.invalid(new ErrorMessage(List.of("Owner not found"), ErrorType.NOT_FOUND));
        }

        Validation<List<String>, Owner> validatedDto = Optional.ofNullable(editOwnerDto)
                .map(OwnerMapper::toOwner)
                .map(this::checkOwner)
                .orElseThrow(() -> new NullPointerException("Given dto is null"));

        if (validatedDto.isInvalid()) {
            return Validation.invalid(new ErrorMessage(validatedDto.getError(), ErrorType.BAD_REQUEST));
        }

        Owner editedOwner = validatedDto.get();
        editedOwner.setId(owner.get().getId());

        return Validation.valid(ownerRepository.save(editedOwner).getId());
    }

    private Optional<Owner> getOwner(String id) {
        return Optional.ofNullable(id)
                .filter(Validator::validateUUID)
                .map(UUID::fromString)
                .flatMap(ownerRepository::findById);
    }

    private Validation<List<String>, Owner> checkOwner(Owner owner) {
        OwnerValidator ov = new OwnerValidator(owner);

        return ov.validate();
    }
}
