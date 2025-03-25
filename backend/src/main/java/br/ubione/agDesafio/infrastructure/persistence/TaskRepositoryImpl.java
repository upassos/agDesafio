package br.ubione.agDesafio.infrastructure.persistence;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.ubione.agDesafio.domain.model.Task;
import br.ubione.agDesafio.domain.repository.TaskRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TaskRepositoryImpl extends JpaRepository<Task, Long>, TaskRepository {
    
	@Query("SELECT t FROM Task t WHERE " +
		       "(:name IS NULL OR t.name LIKE %:name%) AND " +
		       "(:projectId IS NULL OR t.project.id = :projectId) AND " +
		       "(:startDate IS NULL OR t.dtInicio >= :startDate) AND " +
		       "(:endDate IS NULL OR t.dtFim <= :endDate)")
		Page<Task> filterSearch(
		    @Param("name") String name,
		    @Param("projectId") Long projectId,
		    @Param("startDate") Timestamp startDate,
		    @Param("endDate") Timestamp endDate,
		    Pageable pageable
		);

    List<Task> findByProjectId(Long projectId);
}
