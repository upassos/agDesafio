package br.ubione.adDesafio.infraestructure.data;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.model.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("SELECT t FROM Task t " +
	           "WHERE (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
	           "AND (:projectId IS NULL OR t.project.id = :projectId) " +
	           "AND (:dtInicio IS NULL OR t.dtInicio >= :dtInicio) " +
	           "AND (:dtFim IS NULL OR t.dtFim <= :dtFim)")
	    Page<Task> findByFilters(
	        @Param("name") String name,
	        @Param("projectId") Long projectId,
	        @Param("dtInicio") Timestamp dtInicio,
	        @Param("dtFim") Timestamp dtFim,
	        Pageable pageable
	    );
	
	Set<Task> findByProjectId(Long projectId);
}
