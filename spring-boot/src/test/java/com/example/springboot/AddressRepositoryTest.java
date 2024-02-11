package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressRepositoryTest {

    @Autowired
    private AddressRepository sut;

    private final Address address1 = new Address(1L, "Amsterdam");
    private final Address address2 = new Address(2L, "Diemen");

    @BeforeEach
    void setUp() {
        sut.save(address1);
        sut.save(address2);
    }

    @AfterEach
    void cleanUp() {
        sut.deleteAll();
    }

    @Test
    void getByCustomerId() {
        List<Address> actual = sut.getByCustomerId(1L);
        for (Address address : actual) {
            assertThat (address.getCustomerId()).isEqualTo(1L);
        }
    }

    @Test
    void getByCustomerIdNoMatch() {
        List<Address> actual = sut.getByCustomerId(3L);
        assertThat (actual).isEmpty();
    }

    @Test
    @Order(1)
    void updateAddressById() {
        ResponseEntity<String> actual = sut.updateAddressById(1L, "Utrecht");
        assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @Order(2)
    void updateAddressByIdNoMatch() {
        ResponseEntity<String> actual = sut.updateAddressById(1L, "Japan");
        assertThat (actual.getStatusCode().is4xxClientError()).isTrue();
    }
}