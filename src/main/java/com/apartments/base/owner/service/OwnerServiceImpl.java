package com.apartments.base.owner.service;

import com.apartments.base.owner.mapper.OwnerMapper;
import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.repository.OwnerRepository;
import com.apartments.base.owner.validator.OwnerValidator;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class OwnerServiceImpl implements OwnerService{
    private final OwnerRepository ownerRepository;

    @Override
    public Validation<List<String>, UUID> saveOwner(NewOwnerDto newOwnerDto) {
        var owner = Optional.ofNullable(newOwnerDto)
                .map(OwnerMapper::toOwner)
                .map(OwnerValidator::new)
                .map(OwnerValidator::validate)
                .orElseThrow(NullPointerException::new);

        if (owner.isInvalid()) {
            return Validation.invalid(owner.getError());
        } else {
            return Validation.valid(ownerRepository.save(owner.get()).getId());
        }
    }
}
