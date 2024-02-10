package com.example.springboot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    default List<Customer> findAllByName(String name) {
        Iterable<Customer> allCustomers = findAll();
        Iterator<Customer> iterator = allCustomers.iterator();
        List<Customer> customerList = new ArrayList<>();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (Objects.equals(customer.getName(), name)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }

    default List<Customer> findAllByLastName(String lastName) {
        Iterable<Customer> allCustomers = findAll();
        Iterator<Customer> iterator = allCustomers.iterator();
        List<Customer> customerList = new ArrayList<>();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (Objects.equals(customer.getLastName(), lastName)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }

    default List<Customer> findAllByNameAndLastName(String name, String lastName) {
        Iterable<Customer> allCustomers = findAll();
        Iterator<Customer> iterator = allCustomers.iterator();
        List<Customer> customerList = new ArrayList<>();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (Objects.equals(customer.getName(), name) && Objects.equals(customer.getLastName(), lastName)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }

    default ResponseEntity<String> updateEmailById(Long id, String email) {
        Customer customer = findById(id).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>("No customer with id " + id + " was found", HttpStatus.BAD_REQUEST);
        }
        customer.setEmail(email);

        save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
