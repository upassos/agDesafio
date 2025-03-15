package br.ubione.adDesafio.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ubione.adDesafio.model.Task;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public Task save(Task task) { 
		return taskRepository.save(task); 
	}
	
	public List<Task> list() { 
		return taskRepository.findAll(); 
	}
}
