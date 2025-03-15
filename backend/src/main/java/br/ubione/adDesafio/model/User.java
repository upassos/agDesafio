package br.ubione.adDesafio.model;

import jakarta.persistence.*;
import lombok.Data;
import br.ubione.adDesafio.enums.Role;

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

	