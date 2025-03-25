package br.ubione.agDesafio.domain.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ubione.agDesafio.domain.model.Task;

public interface TaskRepository{  

    Page<Task> filterSearch(String name, Long projectId, Timestamp dtInicio, Timestamp dtFim, Pageable pageable);

    List<Task> findByProjectId(Long projectId);

    Optional<Task> findById(Long id);  

    List<Task> findAll(); 

    void deleteById(Long id);  
    
    Task save(Task save);
}
