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
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository sut;

    private final Customer customer1 = new Customer("name 1", "last name 1", 0, "1@email.com");
    private final Customer customer2 = new Customer("name 2", "last name 2", 0, "2@email.com");
    private final Customer customer3 = new Customer("name 1", "last name x", 0, "x@email.com");

    @BeforeEach
    void setUp() {
        sut.save(customer1);
        sut.save(customer2);
        sut.save(customer3);
    }

    @AfterEach
    void cleanUp() {
        sut.deleteAll();
    }

    @Test
    void findAllByName() {
        List<Customer> actual = sut.findAllByName("name 1");
        for (Customer customer : actual) {
            assertThat (customer.getName()).isEqualTo("name 1");
        }
    }

    @Test
    void findAllByNameNoMatch() {
        List<Customer> actual = sut.findAllByName("name 3");
        assertThat (actual).isEmpty();
    }

    @Test
    void findAllByLastName() {
        List<Customer> actual = sut.findAllByLastName("last name 1");
        for (Customer customer : actual) {
            assertThat (customer.getLastName()).isEqualTo("last name 1");
        }
    }

    @Test
    void findAllByLastNameNoMatch() {
        List<Customer> actual = sut.findAllByLastName("last name 3");
        assertThat (actual).isEmpty();
    }

    @Test
    void findAllByNameAndLastName() {
        List<Customer> actual = sut.findAllByNameAndLastName("name 1", "last name 1");
        for (Customer customer : actual) {
            assertThat (customer.getName()).isEqualTo("name 1");
            assertThat (customer.getLastName()).isEqualTo("last name 1");
        }
    }

    @Test
    void findAllByNameAndLastNameNoMatch() {
        List<Customer> actual = sut.findAllByNameAndLastName("name 3", "last name 3");
        assertThat (actual).isEmpty();
    }

    @Test
    @Order(1)
    void updateEmailById() {
        ResponseEntity<String> actual = sut.updateEmailById(1L, "super@gmail.com");
        assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @Order(2)
    void updateEmailByIdNoMatch() {
        ResponseEntity<String> actual = sut.updateEmailById(1L, "super@gmail.com");
        assertThat (actual.getStatusCode().is4xxClientError()).isTrue();
    }
}