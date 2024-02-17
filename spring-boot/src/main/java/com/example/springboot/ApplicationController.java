package com.example.springboot;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ApplicationService applicationService;

    @PostMapping(value = "/add_customer")
    public ResponseEntity<String> postCustomer(@RequestBody CustomerDto customer) {
        Customer savedCustomer = new Customer(customer.getName(), customer.getLastName(), customer.getAge(), customer.getEmail());
        customerRepository.save(savedCustomer);
        return applicationService.parseAddressAndSave(savedCustomer.getId(), customer.getAddress());
    }

    @GetMapping(value = "/get_customer_by_id")
    public ResponseEntity<Customer> getCustomer(@RequestParam(name = "id") Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get_all_customers")
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();

    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "lastName", required = false) String lastName) {
        if (name == null && lastName == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return applicationService.checkIfCustomerFound(name, lastName);
    }

    @PutMapping("/update_email")
    public ResponseEntity<String> updateCustomerEmail(@RequestParam(name = "id") Long customerId, @RequestParam(name = "email") String email) {
        return customerRepository.updateEmailById(customerId, email);
    }

    @PutMapping("/update_address")
    public ResponseEntity<String> updateCustomerAddress(@RequestParam(name = "id") Long id, @RequestParam(name = "address") String address) {
        Address addressToUpdate = addressRepository.findById(id).orElse(null);
        if (addressToUpdate == null) {
            return new ResponseEntity<>("No address with id " + id + " was found", HttpStatus.NOT_FOUND);
        }
        return applicationService.parseAddressAndSaveToExistingAddress(addressToUpdate, address);
    }
}
