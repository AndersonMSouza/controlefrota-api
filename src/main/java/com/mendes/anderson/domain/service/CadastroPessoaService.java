package com.mendes.anderson.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.PessoaNaoEncontradaException;
import com.mendes.anderson.domain.model.Pessoa;
import com.mendes.anderson.domain.repository.PessoaRepository;

@Service
public class CadastroPessoaService {
	
	private static final String MSG_PESSOA_EM_USO 
	= "Pessoa de código %d não pode ser removida, pois está em uso!";

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}	
	
	public void excluir(Long pessoaId) {
		try {
			pessoaRepository.deleteById(pessoaId);
		
		} catch (EmptyResultDataAccessException e) {
			throw new PessoaNaoEncontradaException(pessoaId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_PESSOA_EM_USO, pessoaId));
		}
	}
	
	public Pessoa buscarOuFalhar(@PathVariable Long pessoaId) {
		return pessoaRepository.findById(pessoaId)
			.orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
	}

}
