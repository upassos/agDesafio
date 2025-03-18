package br.ubione.adDesafio.application.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.application.services.CustomerService;
import br.ubione.adDesafio.model.entities.Customer;
import br.ubione.adDesafio.presentation.controller.exception.NoDataException;

@Service
public class CustomerValidate {
	
	private final CustomerService customerService;

	@Autowired
	public CustomerValidate (CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public Customer customerExists(Long customerId) {
		Customer customer = customerService.findById(customerId)
		        .orElseThrow(() -> new NoDataException("O cliente com o ID " + customerId + " não existe."));
		return customer;
	}
}
