package br.ubione.agDesafio.presentation.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;  
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ubione.agDesafio.application.dto.ProjectRequestDTO;
import br.ubione.agDesafio.application.enums.ProjectStatus;
import br.ubione.agDesafio.application.service.ProjectService;
import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.presentation.exception.NoDataException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> list() {
        return ResponseEntity.ok(projectService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        try {
            Project project = projectService.findById(id);
            return ResponseEntity.ok(project);
        } catch (NoDataException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Project> create(@Valid @RequestBody ProjectRequestDTO project) {
        return ResponseEntity.ok(projectService.save(project));
    }

    @PutMapping("/{id}")  
    public ResponseEntity<Project> update(@PathVariable Long id, @Valid @RequestBody ProjectRequestDTO project) {
        try {
            Project updatedProject = projectService.update(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/paginado")
    public ResponseEntity<Page<Project>> getProjects(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "projectStatus", required = false) ProjectStatus projectStatus,  
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {

        Sort sorting = (sort != null) ? Sort.by(sort) : Sort.by("id");

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Project> projects = projectService.getProjectsByFilters(name, customerId, projectStatus, pageable);

        return ResponseEntity.ok(projects);
    }
}
