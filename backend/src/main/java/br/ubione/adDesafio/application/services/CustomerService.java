package br.ubione.adDesafio.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.infraestructure.data.CustomerRepository;
import br.ubione.adDesafio.model.entities.Customer;

@Service
public class CustomerService {
	
    private final CustomerRepository customerRepository;

    
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }
    
    public Page<Customer> findCustomers(String name, String email, Pageable pageable) {
        return customerRepository.findByFilters(name, email, pageable);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
