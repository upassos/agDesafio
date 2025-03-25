package br.ubione.agDesafio.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ubione.agDesafio.application.service.CustomerService;
import br.ubione.agDesafio.domain.model.Customer;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> list() {
        return ResponseEntity.ok(customerService.list());
    }
    
    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> pageableList(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort) {
        
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Customer> customerPage = customerService.findCustomers(name, email, pageable);
        
        return ResponseEntity.ok(customerPage); // Retorna o ResponseEntity corretamente
    }


    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        URI location = URI.create("/customers/" + savedCustomer.getId());
        return ResponseEntity.created(location).body(savedCustomer);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer updatedCustomer) {
        try {
            Customer updated = customerService.update(id, updatedCustomer);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
