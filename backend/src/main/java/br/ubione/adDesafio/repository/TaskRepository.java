package br.ubione.adDesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
}
