package com.example.springboot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> getCustomerByNameIgnoreCase(String name);

    List<Customer> getCustomerByLastNameIgnoreCase(String lastName);

    List<Customer> getCustomerByNameIgnoreCaseAndLastNameIgnoreCase(String name, String lastName);


    default ResponseEntity<String> updateEmailById(Long id, String email) {
        Customer customer = findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>("No customer with id " + id + " was found", HttpStatus.NOT_FOUND);
        }
        customer.setEmail(email);

        save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
