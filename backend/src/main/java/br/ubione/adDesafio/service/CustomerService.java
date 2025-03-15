package br.ubione.adDesafio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ubione.adDesafio.model.Customer;
import br.ubione.adDesafio.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public Customer save(Customer customer) { 
		return customerRepository.save(customer); 
		
	}
	
	public List<Customer> list() { 
		return customerRepository.findAll(); 
	}
}
