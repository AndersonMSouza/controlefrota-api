package com.mendes.anderson.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
import com.mendes.anderson.domain.model.Veiculo;
import com.mendes.anderson.domain.repository.VeiculoRepository;

@Service
public class CadastroVeiculoService {
	
	private static final String MSG_VEICULO_EM_USO 
		= "Veiculo de código %d não pode ser removido, pois está em uso!";
	
	private static final String MSG_VEICULO_NAO_ENCONTRADO 
		= "Não existe veiculo cadastrado com o código %d";


	@Autowired
	private VeiculoRepository veiculoRepository;
	
	public Veiculo salvar(Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}
	
	public void excluir(Long veiculoId) {
		try {
			veiculoRepository.deleteById(veiculoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_VEICULO_NAO_ENCONTRADO, veiculoId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_VEICULO_EM_USO, veiculoId));
		}
	}
	
	public Veiculo buscarOuFalhar(@PathVariable Long veiculoId) {
		return veiculoRepository.findById(veiculoId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_VEICULO_NAO_ENCONTRADO, veiculoId)));
	}
}
