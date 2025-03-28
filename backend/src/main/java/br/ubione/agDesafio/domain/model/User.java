package br.ubione.agDesafio.domain.model;

import br.ubione.agDesafio.application.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
	
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
}

	