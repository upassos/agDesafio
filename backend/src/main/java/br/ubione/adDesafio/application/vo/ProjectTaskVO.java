package br.ubione.adDesafio.application.vo;

import java.util.Set;

import br.ubione.adDesafio.model.entities.Project;
import br.ubione.adDesafio.model.entities.Task;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectTaskVO {

	Project project;
	Set<Task> tasks;
	
}
