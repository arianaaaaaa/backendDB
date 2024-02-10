package com.example.springboot;

import lombok.Getter;

import java.util.List;

@Getter
public class FinalCustomerDto {
    private final Long id;
    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final List<Address> addresses;

    public FinalCustomerDto(Long id, String name, String lastName, int age, String email, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.addresses = addresses;
    }

    public FinalCustomerDto() {
        this.id = null;
        this.name = null;
        this.lastName = null;
        this.age = 0;
        this.email = null;
        this.addresses = null;
    }
}
