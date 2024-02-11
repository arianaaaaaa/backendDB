package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ApplicationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    private ApplicationService sut;

    @Test
    void searchByNameTest() {
        String name = "ariana";

        sut.searchCustomerByValue(name, null);

        verify(customerRepository).findAllByName(name);
        verify(customerRepository, never()).findAllByLastName(any());
        verify(customerRepository, never()).findAllByNameAndLastName(any(), any());
    }

    @Test
    void searchByLastNameTest() {
        String lastName = "vargas";

        sut.searchCustomerByValue(null, lastName);

        verify(customerRepository, never()).findAllByName(any());
        verify(customerRepository).findAllByLastName(lastName);
        verify(customerRepository, never()).findAllByNameAndLastName(any(), any());
    }

    @Test
    void searchByLastNameAndLastNameTest() {
        String name = " ariana";
        String lastName = "vargas";

        sut.searchCustomerByValue(name, lastName);

        verify(customerRepository, never()).findAllByName(any());
        verify(customerRepository, never()).findAllByLastName(any());
        verify(customerRepository).findAllByNameAndLastName(name, lastName);
    }

}