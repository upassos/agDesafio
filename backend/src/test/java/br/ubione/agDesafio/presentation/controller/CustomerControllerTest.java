package br.ubione.agDesafio.presentation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import br.ubione.agDesafio.application.service.CustomerService;
import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.presentation.controller.CustomerController;

@ExtendWith(MockitoExtension.class) // Anotação para inicializar os mocks
class CustomerControllerTest {

    @Mock
    private CustomerService customerService; // Mock do serviço

    @InjectMocks
    private CustomerController customerController; // Injeção do mock no controlador

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
        when(customerService.list()).thenReturn(customerList);

        ResponseEntity<List<Customer>> response = customerController.list();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindById_Success() {
        when(customerService.findById(1L)).thenReturn(Optional.of(customer));

        ResponseEntity<Customer> response = customerController.findById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customer.getName(), response.getBody().getName());
    }

    @Test
    void testFindById_NotFound() {
        when(customerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = customerController.findById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate() {
        when(customerService.save(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.create(customer);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue()); // Status de criação foi alterado para 201
        assertEquals(customer.getName(), response.getBody().getName());
    }

    @Test
    void testDelete() {
        doNothing().when(customerService).delete(1L);

        ResponseEntity<Void> response = customerController.delete(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue()); // Status de sucesso sem corpo
    }

    @Test
    void testPagableList() {
        // Preparando o pageable e a página com um cliente
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<Customer> page = new PageImpl<>(List.of(customer)); // A página com o cliente
        
        // Simulando a resposta do serviço
        when(customerService.findCustomers(any(), any(), eq(pageable))).thenReturn(page);
        
        // Chama o controlador corretamente e espera um ResponseEntity<Page<Customer>>
        ResponseEntity<Page<Customer>> response = customerController.pageableList("John", "john.doe@example.com", 0, 10, "id");
        
        // Assegurando que o ResponseEntity e o conteúdo estão corretos
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Status OK
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(customer.getName(), response.getBody().getContent().get(0).getName());
    }



}
