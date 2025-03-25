package br.ubione.agDesafio.application.vo;

import java.util.Set;

import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.domain.model.Task;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectTaskVO {

	Project project;
	Set<Task> tasks;
	
}
