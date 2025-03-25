package br.ubione.agDesafio.application.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.agDesafio.application.dto.TaskRequestDTO;
import br.ubione.agDesafio.application.validation.ProjectValidade;
import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.domain.model.Task;
import br.ubione.agDesafio.domain.repository.TaskRepository;

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
        return taskRepository.filterSearch(name, projectId, dtInicio, dtFim, pageable);
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

    public Task update(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> existingTaskOpt = taskRepository.findById(id);

        if (existingTaskOpt.isPresent()) {
            Task existingTask = existingTaskOpt.get();
            
            Project project = projectValidate.projectExists(taskRequestDTO.getProjectId());

            existingTask.setName(taskRequestDTO.getName());
            existingTask.setDescription(taskRequestDTO.getDescription());
            existingTask.setDtInicio(taskRequestDTO.getDtInicio());
            existingTask.setDtPrevFim(taskRequestDTO.getDtPrevFim());
            existingTask.setDtFim(taskRequestDTO.getDtFim());
            existingTask.setProject(project);

            return taskRepository.save(existingTask);
        }

        throw new RuntimeException("Não foi localizada nenhuma tarefa com id: " + id);
    }


    
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
    }

    public void delete(Long id) {
    	Optional<Task> task = taskRepository.findById(id);
    	if (task.isPresent()) {
    		taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task não encontrada");
        }
    }
        
    public List<Task> findTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
