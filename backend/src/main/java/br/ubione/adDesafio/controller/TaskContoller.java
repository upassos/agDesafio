package br.ubione.adDesafio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ubione.adDesafio.model.Task;
import br.ubione.adDesafio.repository.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskContoller {
	
	private final TaskService taskService;
	
	public TaskContoller(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping
	public ResponseEntity<Task> criar(@RequestBody Task task) {
	return ResponseEntity.ok(taskService.save(task));
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> listar() {
		return ResponseEntity.ok(taskService.list());
	}
}
