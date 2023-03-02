package com.apartments.base.owner.repository;

import com.apartments.base.owner.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID>, JpaSpecificationExecutor<Owner> {
    @Query("SELECT o FROM Owner o LEFT JOIN FETCH o.apartments a WHERE o.id = :id")
    Optional<Owner> getOwnerEagerlyById(@Param("id")UUID id);
}