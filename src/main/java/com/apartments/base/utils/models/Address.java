package com.apartments.base.utils.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String postcode;
}
