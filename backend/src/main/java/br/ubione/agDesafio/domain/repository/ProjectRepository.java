package br.ubione.agDesafio.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ubione.agDesafio.application.enums.ProjectStatus;
import br.ubione.agDesafio.domain.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Page<Project> findByFilters(String name, Long customerId, ProjectStatus projectStatus, Pageable pageable);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    void deleteById(Long id);

    Project save(Project project);
}
