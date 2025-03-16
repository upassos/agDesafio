package br.ubione.adDesafio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "project")
@Data
public class Project {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDateTime dtInicio;
	private LocalDateTime dtPrevFim;
	private LocalDateTime dtFim;
	private BigDecimal orcamento;
	private BigDecimal custoReal;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<Task> tasks;
}
