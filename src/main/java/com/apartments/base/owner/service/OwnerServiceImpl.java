package com.apartments.base.owner.service;

import com.apartments.base.owner.mapper.OwnerMapper;
import com.apartments.base.owner.models.Owner;
import com.apartments.base.owner.models.dto.*;
import com.apartments.base.owner.repository.OwnerRepository;
import com.apartments.base.owner.specification.OwnerSpecifications;
import com.apartments.base.owner.validator.OwnerValidator;
import com.apartments.base.utils.model.ErrorMessage;
import com.apartments.base.utils.model.ErrorType;
import com.apartments.base.utils.validator.Validator;
import io.vavr.control.Validation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.apartments.base.utils.Converter.toInt;

@Service
@RequiredArgsConstructor
class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerSpecifications ownerSpecifications;

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

    @Override
    public Page<SlimOwnerDto> getOwners(@Valid PageFilterSortOwnerDto requirements) {
        Integer currentPage = toInt(requirements.getPageNumber())
                .filter(pageNumber -> pageNumber >= 0)
                .orElse(0);
        Integer totalElementsOnPage = toInt(requirements.getPageSize())
                .filter(totalElements -> totalElements > 0 && totalElements <= 20)
                .orElse(20);

        Pageable page = PageRequest.of(currentPage, totalElementsOnPage);

        return ownerRepository.findAll(
                combinedSpecification(requirements.getSurname(), requirements.getLastname(), requirements.getCity()),
                page
        ).map(OwnerMapper::toSlimOwnerDto);
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

    private Specification<Owner> combinedSpecification(String surname, String lastname, String city) {
        return Specification.where(ownerSpecifications.mathSurname(surname))
                .and(ownerSpecifications.mathLastName(lastname))
                .and(ownerSpecifications.city(city));
    }
}
