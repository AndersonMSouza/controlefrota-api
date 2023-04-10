package com.mendes.anderson.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.anderson.domain.model.Veiculo;
import com.mendes.anderson.domain.repository.VeiculoRepository;

@Service
public class CadastroPessoaService {
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	public Veiculo salvar(Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}

}
