package br.ubione.adDesafio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ubione.adDesafio.model.Project;
import br.ubione.adDesafio.repository.ProjectRepository;

@Service
public class ProjectService {
	
	private final ProjectRepository projectRepository;
	
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public Project save(Project project) { 
		return projectRepository.save(project); 
	}
	
	public List<Project> list() { 
		return projectRepository.findAll(); 
	}
}
