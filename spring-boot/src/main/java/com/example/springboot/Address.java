package com.example.springboot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long customerId;

    private String streetName;

    private Integer number;

    private String letter;

    private Integer toevoeging;

    private String postCode;

    private String city;


    public Address() {
    }

    public Address(
            long customerId, String streetName, Integer number, String letter,
            Integer toevoeging, String postCode, String city) {
        this.customerId = customerId;
        this.streetName = streetName;
        this.number = number;
        this.letter = letter;
        this.toevoeging = toevoeging;
        this.postCode = postCode;
        this.city = city;
    }
}
