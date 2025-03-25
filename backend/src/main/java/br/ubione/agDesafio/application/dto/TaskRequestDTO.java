package br.ubione.agDesafio.application.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {
	
    @NotNull(message = "O nome da tarefa é obrigatório.")
    @Size(min = 3, max = 120, message = "O nome da tarefa deve ter entre 3 e 120 caracteres.")
    private String name;

    private String description;

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Timestamp dtInicio;

    @FutureOrPresent(message = "A data prevista de término deve ser hoje ou uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Timestamp dtPrevFim;

    @FutureOrPresent(message = "A data de término deve ser hoje ou uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Timestamp dtFim;

    private Long projectId;
}
