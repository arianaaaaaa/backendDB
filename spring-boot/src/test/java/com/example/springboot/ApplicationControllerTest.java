package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
    void postAddressSuccessful() {
        Customer customer = new Customer("name", "lastname", 20, "email");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        ResponseEntity<String> actual = sut.postAddress(1L, "new address");
        assertThat (actual.getStatusCode().is2xxSuccessful()).isTrue();
    }

    void postAddressFail() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> actual = sut.postAddress(1L, "new address");
        assertThat (actual.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void getCustomerSuccessful() {
        Customer customer = new Customer("name", "lastname", 20, "email");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        ResponseEntity<FinalCustomerDto> actual = sut.getCustomer(1L);
        assertThat (actual.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void getAllCustomersFail() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<FinalCustomerDto> actual = sut.getCustomer(1L);
        assertThat (actual.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void searchCustomerNoVariables() {
        ResponseEntity<List<FinalCustomerDto>> actual = sut.searchCustomer(null, null);
        assertThat (actual.getStatusCode().is4xxClientError()).isTrue();
    }
}