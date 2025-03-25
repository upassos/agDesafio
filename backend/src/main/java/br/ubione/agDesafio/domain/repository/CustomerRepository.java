package br.ubione.agDesafio.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ubione.agDesafio.domain.model.Customer;

public interface CustomerRepository {
    Page<Customer> findByFilters(String name, String email, Pageable pageable);
    
    Customer save(Customer entity);
    
    void deleteById(Long id);
    
    List<Customer> findAll();
    
    Optional<Customer> findById(Long id);
}
