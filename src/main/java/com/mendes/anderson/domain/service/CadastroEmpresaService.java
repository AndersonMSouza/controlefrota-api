package com.mendes.anderson.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mendes.anderson.domain.exceptions.EmpresaNaoEncontradaException;
import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.model.Empresa;
import com.mendes.anderson.domain.repository.EmpresaRepository;

@Service
public class CadastroEmpresaService {

	private static final String MSG_EMPRESA_EM_USO 
		= "Empresa de código %d não pode ser removida, pois está em uso!";
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Empresa salvar(Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	public void excluir(Long empresaId) {
		try {
			empresaRepository.deleteById(empresaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EmpresaNaoEncontradaException(empresaId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_EMPRESA_EM_USO, empresaId));
		}
	}
	
	public Empresa buscarOuFalhar(Long empresaId) {
		return empresaRepository.findById(empresaId)
			.orElseThrow(() -> new EmpresaNaoEncontradaException(empresaId));
	}
}
