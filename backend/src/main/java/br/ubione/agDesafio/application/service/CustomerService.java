package br.ubione.agDesafio.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.domain.repository.CustomerRepository;

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

    public Customer save(Customer customer) {
    	if (customer == null) {
            throw new RuntimeException("Cliente não pode ser nulo");
        }
    	
        if (customer.getEMail() == null || !customer.getEMail().contains("@")) {
            throw new RuntimeException("Email inválido");
        }
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente não encontrado");
        }
    }


    public Customer update(Long id, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            
            customer.setName(updatedCustomer.getName());
            customer.setEMail(null);
            
            return customerRepository.save(customer);
        } else {
            throw new RuntimeException("Não foi encontrado cliente com id: " + id);
        }
    }
}
