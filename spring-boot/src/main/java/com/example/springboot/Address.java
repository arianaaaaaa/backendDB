package com.example.springboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private long customerId;

    private String addressString;

    public Address(long customerId, String addressString) {
        this.customerId = customerId;
        this.addressString = addressString;
    }

    public Address() {
    }
}
