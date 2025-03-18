package br.ubione.adDesafio.application.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.application.dto.TaskRequestDTO;
import br.ubione.adDesafio.application.validation.ProjectValidade;
import br.ubione.adDesafio.infraestructure.data.TaskRepository;
import br.ubione.adDesafio.model.entities.Project;
import br.ubione.adDesafio.model.entities.Task;

@Service
public class TaskService {
	
    private final TaskRepository taskRepository;
    private final ProjectValidade projectValidate;
    
    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectValidade projectValidate) {
        this.taskRepository = taskRepository;
        this.projectValidate = projectValidate;
    }

    public Page<Task> list(String name, Long projectId, Timestamp dtInicio, Timestamp dtFim, Pageable pageable) {
        return taskRepository.findByFilters(name, projectId, dtInicio, dtFim, pageable);
    }
    
    public List<Task> list() {
        return taskRepository.findAll();
    }

    public Task save(TaskRequestDTO task) {

    	Project project = projectValidate.projectExists(task.getProjectId());
        
    	Task entity = new Task(task.getName(),
    						   task.getDescription(),
    						   task.getDtInicio(),
    						   task.getDtPrevFim(),
    						   task.getDtFim(),
    						   project);
    	
    	return taskRepository.save(entity);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
    
    public Set<Task> findTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
