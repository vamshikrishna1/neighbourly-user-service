package com.neighbourly.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String flatNumber;
    private String floorNumber;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
