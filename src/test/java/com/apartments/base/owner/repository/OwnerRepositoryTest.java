package com.apartments.base.owner.repository;

import com.apartments.base.owner.OwnerBuilderFactory;
import com.apartments.base.owner.models.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class OwnerRepositoryTest {
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
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