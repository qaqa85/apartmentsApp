package com.apartments.base.owner.specification;

import com.apartments.base.owner.models.Owner;
import com.apartments.base.owner.models.Owner_;
import com.apartments.base.utils.model.Address_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OwnerSpecifications {
    public Specification<Owner> mathSurname(String surnamePrefix) {
        if (Objects.isNull(surnamePrefix)) {
            return null;
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Owner_.SURNAME), surnamePrefix + "%");
    }

    public Specification<Owner> mathLastName(String lastnamePrefix) {
        if (Objects.isNull(lastnamePrefix)) {
            return null;
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Owner_.LASTNAME), lastnamePrefix + "%");
    }

    public Specification<Owner> city(String cityPrefix) {
        if (Objects.isNull(cityPrefix)) {
            return null;
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Owner_.ADDRESS).get(Address_.CITY), cityPrefix + "%");
    }
}
