package br.ubione.agDesafio.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.ubione.agDesafio.application.enums.ProjectStatus;
import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.domain.repository.ProjectRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProjectRepositoryImpl extends JpaRepository<Project, Long>, ProjectRepository {
   
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
