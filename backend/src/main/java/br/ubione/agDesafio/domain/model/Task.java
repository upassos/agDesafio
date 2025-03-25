package br.ubione.agDesafio.domain.model;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

	public Task(String name,
			String description,
			Timestamp dtInicio,
			Timestamp dtPrevFim,
			Timestamp dtFim,
			Project project) {
		this.name		= name;
		this.description= description;
		this.dtInicio   = dtInicio;
		this.dtPrevFim  = dtPrevFim;
		this.dtFim      = dtFim;
		this.project	= project;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")  
    @Column(name = "dt_inicio", nullable = false)
    private Timestamp dtInicio;

    @DateTimeFormat(pattern = "dd/MM/yyyy") 
    @Column(name = "dt_prev_fim", nullable = true)
    private Timestamp dtPrevFim;

    @DateTimeFormat(pattern = "dd/MM/yyyy")  
    @Column(name = "dt_fim", nullable = true)
    private Timestamp dtFim;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)  
    private Project project;
}
