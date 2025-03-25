package br.ubione.agDesafio.domain.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import br.ubione.agDesafio.application.enums.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

	public Project (String name, 
					Timestamp dtInicio, 
						Timestamp dtPrevFim, 
							Timestamp dtFim, 
					BigDecimal orcamento, 
					BigDecimal custoReal,
					ProjectStatus projectStatus,
					Customer customer) {
		this.name 			= name;
		this.dtInicio  	 	= dtInicio;
		this.dtPrevFim  	= dtPrevFim;
		this.dtFim			= dtFim;
		this.orcamento  	= orcamento;
		this.custoReal  	= custoReal;
		this.projectStatus  = projectStatus;
		this.customer		= customer;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "dt_inicio", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Timestamp dtInicio;

    @Column(name = "dt_prev_fim")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Timestamp dtPrevFim;

    @Column(name = "dt_fim")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Timestamp dtFim;

    @Column(name = "orcamento", precision = 15, scale = 2)
    private BigDecimal orcamento;

    @Column(name = "custo_real", precision = 15, scale = 2)
    private BigDecimal custoReal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus projectStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

}
