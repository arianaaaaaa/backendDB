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
public interface AddressRepository extends CrudRepository<Address, Long> {
    default List<Address> getByCustomerId(Long customerId) {
        Iterable<Address> addresses = findAll();
        Iterator<Address> iterator = addresses.iterator();
        List<Address> addressList = new ArrayList<>();
        while (iterator.hasNext()) {
            Address address = iterator.next();
            if (Objects.equals(address.getCustomerId(), customerId)) {
                addressList.add(address);
            }
        }
        return addressList;
    }
    default ResponseEntity<String> updateAddressById(Long addressId, String address){
        Address addressToUpdate = findById(addressId).orElse(null);
        if (addressToUpdate == null) {
            return new ResponseEntity<>("No address with id " + addressId + " was found", HttpStatus.NOT_FOUND);
        }
        addressToUpdate.setAddressString(address);
        save(addressToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
