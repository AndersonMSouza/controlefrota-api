package com.mendes.anderson.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.model.Empresa;
import com.mendes.anderson.domain.repository.EmpresaRepository;


@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping
	public List<Empresa> listar() {
		return empresaRepository.findAll();
	}
	
	public ResponseEntity<Empresa> buscar(@PathVariable Long empresaId) {
		Optional<Empresa> empresa = empresaRepository.findById(empresaId);
		
		if (empresa.isPresent()) {
			return ResponseEntity.ok(empresa.get());
		}
	
		return ResponseEntity.notFound().build();		
	}

}
