package br.ubione.adDesafio.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.model.Project;
import br.ubione.adDesafio.repository.ProjectRepository;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Page<Project> list(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
    
    public Page<Project> getProjectsByFilters(String name, Long customerId, LocalDateTime dtInicio, LocalDateTime dtFim, Pageable pageable) {
        return projectRepository.findByFilters(name, customerId, dtInicio, dtFim, pageable);
    }
}
