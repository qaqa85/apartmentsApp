package com.apartments.base.apartment.models;

import com.apartments.base.owners.Owner;
import com.apartments.base.utils.models.Address;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    @Embedded
    private Address address;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Owner owner;
}
