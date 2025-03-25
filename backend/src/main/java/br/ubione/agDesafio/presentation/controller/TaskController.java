package br.ubione.agDesafio.presentation.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ubione.agDesafio.application.dto.TaskRequestDTO;
import br.ubione.agDesafio.application.service.TaskService;
import br.ubione.agDesafio.domain.model.Task;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<Task>> filterList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Timestamp dtInicio,
            @RequestParam(required = false) Timestamp dtFim,
            Pageable pageable) {
        return ResponseEntity.ok(taskService.list(name, projectId, dtInicio, dtFim, pageable));
    }

    @GetMapping
    public ResponseEntity<List<Task>> list() {
        return ResponseEntity.ok(taskService.list());
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody TaskRequestDTO task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Task updatedTask = taskService.update(id, taskRequestDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
