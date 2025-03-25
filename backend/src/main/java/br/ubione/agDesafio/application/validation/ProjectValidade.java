package br.ubione.agDesafio.application.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ubione.agDesafio.application.service.ProjectService;
import br.ubione.agDesafio.domain.model.Project;

@Service
public class ProjectValidade {
	
	private final ProjectService projectService;

	@Autowired
	public ProjectValidade (ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public Project projectExists(Long projectId) {
	    return projectService.findById(projectId);
	}
}
