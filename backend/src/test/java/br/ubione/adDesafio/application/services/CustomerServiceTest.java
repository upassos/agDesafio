package br.ubione.adDesafio.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ubione.adDesafio.infraestructure.data.CustomerRepository;
import br.ubione.adDesafio.model.entities.Customer;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository; // Mock do repositório

    @InjectMocks
    private CustomerService customerService; // Injeção do mock no serviço

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEMail("john.doe@example.com");
    }

    @Test
    void testList() {
        List<Customer> customerList = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> response = customerService.list();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(customer.getName(), response.get(0).getName());
    }

    @Test
    void testFindCustomers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(customer));
        when(customerRepository.findByFilters(any(), any(), eq(pageable))).thenReturn(page);

        Page<Customer> response = customerService.findCustomers("John", "john.doe@example.com", pageable);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(customer.getName(), response.getContent().get(0).getName());
    }

    @Test
    void testFindById_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> response = customerService.findById(1L);

        assertTrue(response.isPresent());
        assertEquals(customer.getName(), response.get().getName());
    }

    @Test
    void testFindById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> response = customerService.findById(1L);

        assertFalse(response.isPresent());
    }

    @Test
    void testSave() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer response = customerService.save(customer);

        assertNotNull(response);
        assertEquals(customer.getName(), response.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.delete(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}
