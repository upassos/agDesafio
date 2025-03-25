package br.ubione.agDesafio.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.domain.repository.CustomerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustomerRepositoryImpl extends JpaRepository<Customer, Long>, CustomerRepository {
    
    @Query("SELECT c FROM Customer c WHERE " +
           "(:name IS NULL OR c.name LIKE %:name%) AND " +
           "(:email IS NULL OR c.eMail LIKE %:email%)")
    Page<Customer> findByFilters(@Param("name") String name, 
                                 @Param("email") String email, 
                                 Pageable pageable);
}
