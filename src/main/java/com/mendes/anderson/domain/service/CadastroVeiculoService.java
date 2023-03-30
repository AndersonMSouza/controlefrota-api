package com.mendes.anderson.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
import com.mendes.anderson.domain.model.Veiculo;
import com.mendes.anderson.domain.repository.VeiculoRepository;

@Service
public class CadastroVeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	public Veiculo salvar(Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}
	
	public void excluir(Long veiculoId) {
		try {
			veiculoRepository.deleteById(veiculoId);
		}  catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe veiculo cadastrado com o código %d", veiculoId));
			
			} catch (DataIntegrityViolationException e) {
				throw new EntidadeEmUsoException(
					String.format("Veiculo de código %d não pode ser removido, pois está em uso!", veiculoId));
			}
	}
}
