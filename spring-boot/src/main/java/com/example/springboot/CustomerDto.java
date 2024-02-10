package com.example.springboot;

import lombok.Getter;

@Getter
public class CustomerDto {
    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String address;

    public CustomerDto(String name, String lastName, int age, String email, String address) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.address = address;
    }
}
