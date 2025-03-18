package br.ubione.adDesafio.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.application.dto.ProjectRequestDTO;
import br.ubione.adDesafio.application.enums.ProjectStatus;
import br.ubione.adDesafio.application.validation.CustomerValidate;
import br.ubione.adDesafio.infraestructure.data.ProjectRepository;
import br.ubione.adDesafio.model.entities.Customer;
import br.ubione.adDesafio.model.entities.Project;

@Service
public class ProjectService {
    
	private final ProjectRepository projectRepository;
	private final CustomerValidate customerValidade;
	
	@Autowired
    public ProjectService(ProjectRepository projectRepository, CustomerValidate customerValidade) {
        this.projectRepository = projectRepository;
        this.customerValidade  = customerValidade;
    }

    public List<Project> list() {
    	return projectRepository.findAll();
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public Project save(ProjectRequestDTO project) {
    		
    	Customer customer = customerValidade.customerExists(project.getCustomerId());
    	
    	Project entity = new Project(
    	        project.getName(),
    	        project.getDtInicio(),
    	        project.getDtPrevFim(),
    	        project.getDtFim(),
    	        project.getOrcamento(),
    	        project.getCustoReal(),
    	        project.getProjectStatus(),
    	        customer
    	);

    	return projectRepository.save(entity);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
    
    public Page<Project> getProjectsByFilters(String name, Long customerId, ProjectStatus projectStatus, Pageable pageable) {
        return projectRepository.findByFilters(name, customerId, projectStatus, pageable);
    }
}
