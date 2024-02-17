package com.example.springboot;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.regex.Matcher;

import static org.assertj.core.api.Assertions.assertThat;
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

        sut.checkIfCustomerFound(name, null);

        verify(customerRepository).getCustomerByNameIgnoreCase(name);
        verify(customerRepository, never()).getCustomerByLastNameIgnoreCase(any());
        verify(customerRepository, never()).getCustomerByNameIgnoreCaseAndLastNameIgnoreCase(any(), any());
    }

    @Test
    void searchByLastNameTest() {
        String lastName = "vargas";

        sut.checkIfCustomerFound(null, lastName);

        verify(customerRepository, never()).getCustomerByNameIgnoreCase(any());
        verify(customerRepository).getCustomerByLastNameIgnoreCase(lastName);
        verify(customerRepository, never()).getCustomerByNameIgnoreCaseAndLastNameIgnoreCase(any(), any());
    }

    @Test
    void searchByLastNameAndLastNameTest() {
        String name = " ariana";
        String lastName = "vargas";

        sut.checkIfCustomerFound(name, lastName);

        verify(customerRepository, never()).getCustomerByNameIgnoreCase(any());
        verify(customerRepository, never()).getCustomerByLastNameIgnoreCase(any());
        verify(customerRepository).getCustomerByNameIgnoreCaseAndLastNameIgnoreCase(name, lastName);
    }

    @Test
    void parseAddressSuccess() {
        String address = "1rst Nice-Street 491 b1, 1112ZJ Diemen Zuid";
        Matcher m = sut.parseAddress(address);
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo("1rst Nice-Street");
        assertThat(m.group(2)).isEqualTo("491");
        assertThat(m.group(3)).isEqualTo("b");
        assertThat(m.group(4)).isEqualTo("1");
        assertThat(m.group(5)).isEqualTo("1112ZJ");
        assertThat(m.group(6)).isEqualTo("Diemen Zuid");
    }

    @Test
    void parseAddressFail() {
        String address = "Charley Tooropgracht 491, Diemen";
        Matcher m = sut.parseAddress(address);
        assertThat(m.find()).isFalse();
    }

    @Test
    void parseAddressWithLetter() {
        String address = "Charley Tooropgracht 491 B, 1112ZJ Diemen";
        Matcher m = sut.parseAddress(address);
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo("Charley Tooropgracht");
        assertThat(m.group(2)).isEqualTo("491");
        assertThat(m.group(3)).isEqualTo("B");
        assertThat(m.group(4)).isNull();
        assertThat(m.group(5)).isEqualTo("1112ZJ");
        assertThat(m.group(6)).isEqualTo("Diemen");
    }

    @Test
    void parseAddressWithToevoegining() {
        String address = "Charley Tooropgracht 491 1, 1112ZJ Diemen";
        Matcher m = sut.parseAddress(address);
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo("Charley Tooropgracht");
        assertThat(m.group(2)).isEqualTo("491");
        assertThat(m.group(3)).isNull();
        assertThat(m.group(4)).isEqualTo("1");
        assertThat(m.group(5)).isEqualTo("1112ZJ");
        assertThat(m.group(6)).isEqualTo("Diemen");
    }

    @Test
    void parseAddressWithLetterAndToevoegining() {
        String address = "Charley Tooropgracht 491B 1, 1112ZJ Diemen";
        Matcher m = sut.parseAddress(address);
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo("Charley Tooropgracht");
        assertThat(m.group(2)).isEqualTo("491");
        assertThat(m.group(3)).isEqualTo("B");
        assertThat(m.group(4)).isEqualTo("1");
        assertThat(m.group(5)).isEqualTo("1112ZJ");
        assertThat(m.group(6)).isEqualTo("Diemen");
    }

    @Test
    void parseAddressSeparatedPostCode() {
        String address = "Charley Tooropgracht 491, 1112 ZJ Diemen";
        Matcher m = sut.parseAddress(address);
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo("Charley Tooropgracht");
        assertThat(m.group(2)).isEqualTo("491");
        assertThat(m.group(3)).isNull();
        assertThat(m.group(4)).isNull();
        assertThat(m.group(5)).isEqualTo("1112 ZJ");
        assertThat(m.group(6)).isEqualTo("Diemen");
    }

    @Test
    void updateAddress() {
        Address address = new Address(1l, "Street", 1, "A", 1, "1111 AA", "City");
        sut.parseAddressAndSaveToExistingAddress(address, "Second Street 2 B 2, 2222BB Second City");
        address.setStreetName("Second Street");
        address.setNumber(2);
        address.setLetter("B");
        address.setToevoeging(2);
        address.setPostCode("2222BB");
        address.setCity("Second City");
        verify(addressRepository).save(address);
    }

    @Test
    void updateAddressFail() {
        Address address = new Address(1l, "Street", 1, "A", 1, "1111 AA", "City");
        ResponseEntity<String> actual = sut.parseAddressAndSaveToExistingAddress(address, "Second Street 2 2222BB Second City");
        assertThat(actual.getStatusCode().is4xxClientError()).isTrue();
        verify(addressRepository, never()).save(any());
    }

    @Test
    void parseAddressAndSaveSuccess() {
        String address = "Charley Tooropgracht 491, 1112ZJ Diemen";

        ResponseEntity<String> actual = sut.parseAddressAndSave(1L, address);

        assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void parseAddressAndSaveFail() {
        String address = "Charley Tooropgracht 1112ZJ Diemen";

        ResponseEntity<String> actual = sut.parseAddressAndSave(1L, address);

        assertThat(actual.getStatusCode().is4xxClientError()).isTrue();
    }
}