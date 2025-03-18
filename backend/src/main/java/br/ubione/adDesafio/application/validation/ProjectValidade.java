package br.ubione.adDesafio.application.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.application.services.ProjectService;
import br.ubione.adDesafio.model.entities.Project;
import br.ubione.adDesafio.presentation.controller.exception.NoDataException;

@Service
public class ProjectValidade {
	
	private final ProjectService projectService;

	@Autowired
	public ProjectValidade (ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public Project projectExists(Long projectId) {
		Project project = projectService.findById(projectId)
		        .orElseThrow(() -> new NoDataException("O projeto com o ID " + projectId + " não existe."));
		return project;
	}
}
