package com.mendes.anderson.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/{empresaId}")
	public Empresa buscar(@PathVariable Long empresaId) {
		return cadastroEmpresaService.buscarOuFalhar(empresaId);		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa adicionar(@RequestBody Empresa empresa) {
		return cadastroEmpresaService.salvar(empresa);			
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
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long empresaId) {
		cadastroEmpresaService.excluir(empresaId);			
	}
	
}
