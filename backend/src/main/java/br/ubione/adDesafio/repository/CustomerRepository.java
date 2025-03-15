package br.ubione.adDesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
