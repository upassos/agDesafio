package br.ubione.adDesafio.infraestructure.data;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.model.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
   
    @Query("SELECT p FROM Project p " +
            "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:customerId IS NULL OR p.customer.id = :customerId) " +
            "AND (:dtInicio IS NULL OR p.dtInicio >= :dtInicio) " +
            "AND (:dtFim IS NULL OR p.dtFim <= :dtFim)")
     Page<Project> findByFilters(
         @Param("name") String name,
         @Param("customerId") Long customerId,
         @Param("dtInicio") LocalDateTime dtInicio,
         @Param("dtFim") LocalDateTime dtFim,
         Pageable pageable
     );

}
