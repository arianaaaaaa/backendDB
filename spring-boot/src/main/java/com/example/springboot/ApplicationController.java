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
    public void postCustomer(@RequestBody CustomerDto customer) {
        Customer savedCustomer = new Customer(customer.getName(), customer.getLastName(), customer.getAge(), customer.getEmail());
        customerRepository.save(savedCustomer);
        addressRepository.save(new Address(savedCustomer.getId(), customer.getAddress()));
    }

    @PostMapping(value = "/add_address_to_customer")
    public void postAddress(@RequestParam(name = "id") Long id, @RequestParam(name = "address") String address) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            addressRepository.save(new Address(customer.getId(), address));
        }
    }

    @GetMapping(value = "/get_customer_by_id")
    public ResponseEntity<FinalCustomerDto> getCustomer(@RequestParam(name = "id") Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(applicationService.getFinalCustomer(customer), HttpStatus.OK);
    }

    @GetMapping(value = "/get_all_customers")
    public List<FinalCustomerDto> getAllCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        return applicationService.getFinalCustomers(customers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FinalCustomerDto>> searchCustomer(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "lastName", required = false) String lastName) {
        if (name == null && lastName == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(applicationService.searchCustomerByValue(name, lastName), HttpStatus.OK);
    }

    @GetMapping("/update_email")
    public ResponseEntity<String> updateCustomerEmail(@RequestParam(name = "id") Long customerId, @RequestParam(name = "email") String email) {
        return customerRepository.updateEmailById(customerId, email);
    }

    @GetMapping("/update_address")
    public ResponseEntity<String> updateCustomerAddress(@RequestParam(name = "id") Long id, @RequestParam(name = "address") String address) {
        return addressRepository.updateAddressById(id, address);
    }
}
