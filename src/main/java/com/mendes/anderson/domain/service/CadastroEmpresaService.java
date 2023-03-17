package com.mendes.anderson.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
import com.mendes.anderson.domain.model.Empresa;
import com.mendes.anderson.domain.repository.EmpresaRepository;

@Service
public class CadastroEmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Empresa salvar(Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	public void excluir(Long empresaId) {
		try {
		empresaRepository.deleteById(empresaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe empresa cadastrada com o código %d", empresaId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Empresa de código %d não pode ser removida, pois está em uso!", empresaId));
		}
	}
}
