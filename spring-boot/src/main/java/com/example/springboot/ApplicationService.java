package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private static final String ERROR_MESSAGE = "Please input a correct address format.\n " +
            "Street 50 X, 1112ZJ City (X is optional and can be a letter, a number or both)";


    ResponseEntity<List<Customer>> checkIfCustomerFound(String name, String lastName) {
        List<Customer> customer = new ArrayList<>();
        if (name != null && lastName == null) {
            customer = customerRepository.getCustomerByNameIgnoreCase(name);
        } else if (name == null && lastName != null) {
            customer = customerRepository.getCustomerByLastNameIgnoreCase(lastName);
        } else if (name != null) {
            customer = customerRepository.getCustomerByNameIgnoreCaseAndLastNameIgnoreCase(name, lastName);
        }
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    ResponseEntity<String> parseAddressAndSave(Long customerID, String address) {
        Matcher m = parseAddress(address);
        if (m.find()) {
            Integer toevoegining = m.group(4) != null ? Integer.parseInt(m.group(4)) : null;
            addressRepository.save(new Address(customerID, m.group(1), Integer.parseInt(m.group(2)), m.group(3),
                    toevoegining, m.group(5), m.group(6)));
            return new ResponseEntity<>("Customer with id " + customerID + " was created ", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    Matcher parseAddress(String address) {
        Pattern pattern = Pattern.compile(
                "([A-Za-z-0-9']+\\s?[A-Za-z-0-9']+?\\s?[A-Za-z-0-9']+?\\s?[A-Za-z-0-9']+?\\s?[A-Za-z-0-9']+?)" +
                        "\\s(\\d+)" +
                        "\\s?([A-Za-z]{1,2})?" +
                        "\\s?(\\d{1,2})?" +
                        ",\\s?(\\d{4}\\s?[A-Za-z]{2})" +
                        "\\s?(.*)");
        return pattern.matcher(address);
    }

    ResponseEntity<String> parseAddressAndSaveToExistingAddress(Address address, String newAddress) {
        Matcher m = parseAddress(newAddress);
        if (m.find()) {
            Integer toevoegining = m.group(4) != null ? Integer.parseInt(m.group(4)) : null;
            address.setStreetName(m.group(1));
            address.setNumber(Integer.parseInt(m.group(2)));
            address.setLetter(m.group(3));
            address.setToevoeging(toevoegining);
            address.setPostCode(m.group(5));
            address.setCity(m.group(6));
            addressRepository.save(address);
            return new ResponseEntity<>("Address for id " + address.getId() + " updated", HttpStatus.OK);
        }
        return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
