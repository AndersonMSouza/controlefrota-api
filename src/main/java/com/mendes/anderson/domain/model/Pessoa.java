package com.mendes.anderson.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String sobrenome;
	
	@Column(nullable = false)
	private String cpf;
	
	@Column(nullable = false)
	private String rg;
	
	@Column(nullable = false)
	private String estadoCivil;
	
	@Column(nullable = false)
	private String naturalidade;
	
	@Column(nullable = false)
	private String nacionalidade;
	
}
