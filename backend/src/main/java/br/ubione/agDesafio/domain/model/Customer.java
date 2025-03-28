package br.ubione.agDesafio.domain.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 80)
    @NotBlank(message = "O nome não pode estar vazio") 
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres") 
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    @NotBlank(message = "O e-mail não pode estar vazio") 
    @Email(message = "O e-mail deve ser válido") 
    private String eMail;
    
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Project> projects;
}

