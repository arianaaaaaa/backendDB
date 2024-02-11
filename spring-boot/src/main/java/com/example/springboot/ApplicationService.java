package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    FinalCustomerDto getFinalCustomer(Customer customer) {
        List<Address> addresList = addressRepository.getByCustomerId(customer.getId());

        return new FinalCustomerDto(customer.getId(), customer.getName(), customer.getLastName(), customer.getAge(), customer.getEmail(), addresList);
    }

    List<FinalCustomerDto> getFinalCustomers(Iterable<Customer> customers) {
        List<FinalCustomerDto> finalCustomerDtoList = new ArrayList<>();
        for (Customer customer : customers) {
            finalCustomerDtoList.add(getFinalCustomer(customer));
        }
        return finalCustomerDtoList;
    }

    List<FinalCustomerDto> searchCustomerByValue(String name, String lastName) {
        if (name != null && lastName == null) {
            return getFinalCustomers(customerRepository.findAllByName(name));
        } else if (name == null && lastName != null) {
            return getFinalCustomers(customerRepository.findAllByLastName(lastName));
        }
        return getFinalCustomers(customerRepository.findAllByNameAndLastName(name, lastName));
    }

    ResponseEntity<List<FinalCustomerDto>> checkIfCustomerFound(String name, String lastName) {
        List<FinalCustomerDto> customer = searchCustomerByValue(name, lastName);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
