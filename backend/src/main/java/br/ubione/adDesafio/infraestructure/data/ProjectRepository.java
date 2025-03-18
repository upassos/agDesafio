package br.ubione.adDesafio.infraestructure.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.application.enums.ProjectStatus;
import br.ubione.adDesafio.model.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
   
	@Query("SELECT p FROM Project p " +
		       "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
		       "AND (:customerId IS NULL OR p.customer.id = :customerId) " +
		       "AND (:projectStatus IS NULL OR p.projectStatus = :projectStatus)")
		Page<Project> findByFilters(
		    @Param("name") String name,
		    @Param("customerId") Long customerId,
		    @Param("projectStatus") ProjectStatus projectStatus,
		    Pageable pageable
		);

}
