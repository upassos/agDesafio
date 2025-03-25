package br.ubione.agDesafio.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

import br.ubione.agDesafio.application.service.CustomerService;
import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.domain.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository; 

    @InjectMocks
    private CustomerService customerService; 

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Aluísio Cipriano");
        customer.setEMail("acipriano@meudominio.com");
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

        Page<Customer> response = customerService.findCustomers("Aluísio", "acipriano@meudominio.com", pageable);

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
    public void testDelete() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        doNothing().when(customerRepository).deleteById(1L);

        customerService.delete(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.delete(1L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void testUpdate_Success() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("Maicon Mendel");
        updatedCustomer.setEMail("mmendel@codecsharp.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer response = customerService.update(1L, updatedCustomer);

        assertNotNull(response);
        assertEquals("Maicon Mendel", response.getName());
        assertEquals("mmendel@codecsharp.com", response.getEMail());
    }

    @Test
    void testUpdate_NotFound() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("Larazo Romanel");
        updatedCustomer.setEMail("lr@romanel.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.update(1L, updatedCustomer);
        });

        assertEquals("Não foi encontrado cliente com id: 1", exception.getMessage());
    }

    @Test
    void testSave_InvalidEmail() {
        customer.setEMail("email-invalido");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.save(customer);
        });

        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    void testSave_NullCustomer() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.save(null);
        });

        assertEquals("Cliente não pode ser nulo", exception.getMessage());
    }

    @Test
    void testFindCustomers_EmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of());
        when(customerRepository.findByFilters(any(), any(), eq(pageable))).thenReturn(page);

        Page<Customer> response = customerService.findCustomers("Inexistente", "inexistente@vaiachar.com", pageable);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
    }

    @Test
    void testList_EmptyList() {
        when(customerRepository.findAll()).thenReturn(List.of());

        List<Customer> response = customerService.list();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void testSave_ExceptionInRepository() {
        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.save(customer);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testDelete_VerifyDeleteCalledOnce() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(1L);

        customerService.delete(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}
