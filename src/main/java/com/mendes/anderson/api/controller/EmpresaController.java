package com.mendes.anderson.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
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
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Empresa empresa) {
		try {
			empresa = empresaRepository.save(empresa);
			return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{empresaId}")
	public ResponseEntity<?> atualizar(@PathVariable Long empresaId,
			@RequestBody Empresa empresa) {
		try {			
			Empresa empresaAtual = empresaRepository.findById(empresaId).orElse(null);
			
			if (empresaAtual != null) {
				BeanUtils.copyProperties(empresa, empresaAtual, "id");
				
				empresaAtual = empresaRepository.save(empresaAtual);
				return ResponseEntity.ok(empresaAtual);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
				.body(e.getMessage());
		}
	}
}
