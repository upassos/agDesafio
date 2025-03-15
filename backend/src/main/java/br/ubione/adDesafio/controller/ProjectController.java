package br.ubione.adDesafio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ubione.adDesafio.model.Project;
import br.ubione.adDesafio.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	private final ProjectService projectService;
	
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@PostMapping
	public ResponseEntity<Project> criar(@RequestBody Project project) {
		return ResponseEntity.ok(projectService.save(project));
	}
	
	@GetMapping
	public ResponseEntity<List<Project>> listar() {
		return ResponseEntity.ok(projectService.list());
	}
}
