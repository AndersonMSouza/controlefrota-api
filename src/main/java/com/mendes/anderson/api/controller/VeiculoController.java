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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.exceptions.EntidadeEmUsoException;
import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
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
	public ResponseEntity<?> atualizar(@PathVariable Long veiculoId, @RequestBody Veiculo veiculo) {
		try {
			Optional<Veiculo> veiculoAtual = veiculoRepository.findById(veiculoId);
			
			if (veiculoAtual.isPresent()) {
				BeanUtils.copyProperties(veiculo, veiculoAtual.get(), "id");
				Veiculo veiculoSalvo = veiculoRepository.save(veiculoAtual.get());
				return ResponseEntity.ok(veiculoSalvo);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{veiculoId}")
	public ResponseEntity<Veiculo> remover(@PathVariable Long veiculoId) {
		try {
			cadastroVeiculoService.excluir(veiculoId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}		
	}
}
