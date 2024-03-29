package com.mendes.anderson.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.model.Veiculo;
import com.mendes.anderson.domain.repository.VeiculoRepository;
import com.mendes.anderson.domain.service.CadastroVeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private CadastroVeiculoService cadastroVeiculoService;
	
	@GetMapping
	public List<Veiculo> listar() {
		return veiculoRepository.findAll();
	}
	
	@GetMapping("/{veiculoId}")
	public Veiculo buscar(@PathVariable Long veiculoId) {
		return cadastroVeiculoService.buscarOuFalhar(veiculoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Veiculo adicionar(@RequestBody Veiculo veiculo) {
		return cadastroVeiculoService.salvar(veiculo);
	}
	
	@PutMapping("/{veiculoId}")
	public Veiculo atualizar(@PathVariable Long veiculoId, 
		@RequestBody Veiculo veiculo) {
		Veiculo veiculoAtual = veiculoRepository.findById(veiculoId).orElse(null);	
		
		BeanUtils.copyProperties(veiculo, veiculoAtual, "id");
			
		return cadastroVeiculoService.salvar(veiculoAtual);
	}
	
	@DeleteMapping("/{veiculoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long veiculoId) {		
		cadastroVeiculoService.excluir(veiculoId);			
	}
	
}
