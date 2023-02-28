package com.apartments.base.owner.models;

import com.apartments.base.apartment.models.Apartment;
import com.apartments.base.utils.model.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor @AllArgsConstructor
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
