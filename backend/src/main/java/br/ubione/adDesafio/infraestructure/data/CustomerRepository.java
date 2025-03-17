package br.ubione.adDesafio.infraestructure.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.model.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	    
	
	/*Usei query para não ter que ficar fazendo validações na service para verificar se os campos 
	que compõem os filtros foram preenchidos */
	@Query("SELECT c FROM Customer c WHERE " +
	           "(:name IS NULL OR c.name LIKE %:name%) AND " +
	           "(:email IS NULL OR c.eMail LIKE %:email%)")
	Page<Customer> findByFilters(@Param("name") String name, 
	                                 @Param("email") String email, 
	                                 Pageable pageable);
	
}
