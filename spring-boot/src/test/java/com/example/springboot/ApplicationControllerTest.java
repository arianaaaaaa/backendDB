package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ApplicationControllerTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController sut;


    @Test
    void searchCustomerNoVariables() {
        ResponseEntity<List<Customer>> actual = sut.searchCustomer(null, null);
        assertThat(actual.getStatusCode().is4xxClientError()).isTrue();
    }
}