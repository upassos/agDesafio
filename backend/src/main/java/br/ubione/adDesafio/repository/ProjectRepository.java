package br.ubione.adDesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ubione.adDesafio.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
