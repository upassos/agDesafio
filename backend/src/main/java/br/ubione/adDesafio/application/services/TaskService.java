package br.ubione.adDesafio.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ubione.adDesafio.infraestructure.data.TaskRepository;
import br.ubione.adDesafio.model.entities.Task;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> list(String name, Long projectId, LocalDateTime dtInicio, LocalDateTime dtFim, Pageable pageable) {
        return taskRepository.findByFilters(name, projectId, dtInicio, dtFim, pageable);
    }
    
    public List<Task> list() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
}
