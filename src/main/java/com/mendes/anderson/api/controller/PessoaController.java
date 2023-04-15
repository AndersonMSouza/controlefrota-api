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

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
import com.mendes.anderson.domain.model.Pessoa;
import com.mendes.anderson.domain.repository.PessoaRepository;
import com.mendes.anderson.domain.service.CadastroPessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CadastroPessoaService cadastroPessoaService;
	
	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{pessoaId}")
	public Pessoa buscar(@PathVariable Long pessoaId) {
		return cadastroPessoaService.buscarOuFalhar(pessoaId);			
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pessoa adicionar(@RequestBody Pessoa pessoa) {
		return cadastroPessoaService.salvar(pessoa);
	}
	
	@PutMapping("/{pessoaId}")
	public Pessoa atualizar(@PathVariable Long pessoaId,
		@RequestBody Pessoa pessoa) {
		
		Pessoa pessoaAtual = pessoaRepository.findById(pessoaId).orElse(null);
			
		BeanUtils.copyProperties(pessoa, pessoaAtual, "id");
		
		return cadastroPessoaService.salvar(pessoaAtual);
	}
	
	@DeleteMapping("/{pessoaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long pessoaId) {
		cadastroPessoaService.excluir(pessoaId);
	}	
}
