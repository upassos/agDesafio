package br.ubione.adDesafio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ubione.adDesafio.model.Customer;
import br.ubione.adDesafio.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping
	public ResponseEntity<Customer> criar(@RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.save(customer));
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> listar() {
		return ResponseEntity.ok(customerService.list());
	}
}
