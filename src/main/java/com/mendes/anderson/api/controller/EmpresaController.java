package com.mendes.anderson.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
import com.mendes.anderson.domain.model.Empresa;
import com.mendes.anderson.domain.repository.EmpresaRepository;
import com.mendes.anderson.domain.service.CadastroEmpresaService;


@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private CadastroEmpresaService cadastroEmpresaService;
	
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
			empresa = cadastroEmpresaService.salvar(empresa);
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
				
				empresaAtual = cadastroEmpresaService.salvar(empresaAtual);
				return ResponseEntity.ok(empresaAtual);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
				.body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{empresaId}")
	public ResponseEntity<Empresa> remover(@PathVariable Long empresaId) {
		try {
			cadastroEmpresaService.excluir(empresaId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
