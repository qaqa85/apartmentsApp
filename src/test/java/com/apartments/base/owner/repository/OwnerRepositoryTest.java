package com.apartments.base.owner.repository;

import com.apartments.base.owner.OwnerBuilderFactory;
import com.apartments.base.owner.models.Owner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class OwnerRepositoryTest {
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("Should save new owner to database")
    @Disabled("There is no point to test Spring JPA")
    void shouldSaveNewOwnerToDatabase() {
        // GIVEN
        Owner owner = OwnerBuilderFactory.getDefaultOwnerBuilder().id(null).build();

        // WHEN
        Owner write = ownerRepository.save(owner);
        Optional<Owner> read = ownerRepository.findById(write.getId());

        // THEN
        assertTrue(read.isPresent());
        assertThat(read.get().getId()).isNotNull();
    }
}