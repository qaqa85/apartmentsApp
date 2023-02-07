package com.apartments.base.owners;

import com.apartments.base.apartment.models.Apartment;
import com.apartments.base.utils.models.Address;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String surname;
    private String lastname;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Apartment> apartments;
    private String phoneNumber;
    @Embedded
    private Address address;
}
