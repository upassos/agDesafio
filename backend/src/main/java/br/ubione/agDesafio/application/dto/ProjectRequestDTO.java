package br.ubione.agDesafio.application.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.ubione.agDesafio.application.enums.ProjectStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequestDTO {
	
	@NotBlank(message = "O nome do projeto é obrigatório.")
    @Size(max = 120, message = "O nome do projeto não deve exceder 120 caracteres.")
    private String name;

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Timestamp dtInicio;

    @FutureOrPresent(message = "A data prevista de término deve ser hoje ou uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Timestamp dtPrevFim;

    @FutureOrPresent(message = "A data de término deve ser hoje ou uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Timestamp dtFim;

    @DecimalMin(value = "0.0", inclusive = false, message = "O orçamento deve ser maior que zero.")
    private BigDecimal orcamento;

    @DecimalMin(value = "0.0", inclusive = false, message = "O custo real deve ser maior que zero.")
    private BigDecimal custoReal;
    
    @NotNull(message = "O status do projeto é obrigatório.")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @NotNull(message = "O id do cliente é obrigatório.")
    private Long customerId;

}
