package br.ubione.agDesafio.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.agDesafio.application.dto.ProjectRequestDTO;
import br.ubione.agDesafio.application.enums.ProjectStatus;
import br.ubione.agDesafio.application.validation.CustomerValidate;
import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.domain.repository.ProjectRepository;
import br.ubione.agDesafio.presentation.exception.NoDataException;

@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final CustomerValidate customerValidade;
    
    @Autowired
    public ProjectService(ProjectRepository projectRepository, CustomerValidate customerValidade) {
        this.projectRepository = projectRepository;
        this.customerValidade = customerValidade;
    }

    public List<Project> list() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new NoDataException("Projeto não encontrado"));
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

        Project retorno = projectRepository.save(entity);
        
        return retorno;
    }

    public void delete(Long id) {
    	Optional<Project> project = projectRepository.findById(id);
    	if (project.isPresent()) {
    		projectRepository.deleteById(id);
        } else {
            throw new RuntimeException("Projeto não encontrado");
        }
    }   
    
    public Page<Project> getProjectsByFilters(String name, Long customerId, ProjectStatus projectStatus, Pageable pageable) {
        return projectRepository.findByFilters(name, customerId, projectStatus, pageable);
    }

    public Project update(Long id, ProjectRequestDTO project) {
        Optional<Project> existingProjectOpt = projectRepository.findById(id);
        
        if (existingProjectOpt.isPresent()) {
            Project existingProject = existingProjectOpt.get();
            
            Customer customer = customerValidade.customerExists(project.getCustomerId());
            
            existingProject.setName(project.getName());
            existingProject.setDtInicio(project.getDtInicio());
            existingProject.setDtPrevFim(project.getDtPrevFim());
            existingProject.setDtFim(project.getDtFim());
            existingProject.setOrcamento(project.getOrcamento());
            existingProject.setCustoReal(project.getCustoReal());
            existingProject.setProjectStatus(project.getProjectStatus());
            existingProject.setCustomer(customer);

            return projectRepository.save(existingProject);
        }
        
        throw new RuntimeException("Não foi localizado nenhum projeto com id: " + id);
    }
}
