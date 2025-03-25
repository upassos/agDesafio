package br.ubione.agDesafio.application.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ubione.agDesafio.application.service.CustomerService;
import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.presentation.exception.NoDataException;

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
