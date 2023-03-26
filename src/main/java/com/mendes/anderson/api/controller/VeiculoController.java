package com.mendes.anderson.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.exceptions.EntidadeNaoEncontradaException;
import com.mendes.anderson.domain.model.Veiculo;
import com.mendes.anderson.domain.repository.VeiculoRepository;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@GetMapping
	public List<Veiculo> listar() {
		return veiculoRepository.findAll();
	}
	
	@GetMapping("/{veiculoId}")
	public ResponseEntity<Veiculo> buscar(@PathVariable Long veiculoId) {
		Optional<Veiculo> veiculo = veiculoRepository.findById(veiculoId);
	
		if (veiculo.isPresent()) {
			return ResponseEntity.ok(veiculo.get()); 
		}
		
		return ResponseEntity.notFound().build();
	
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Veiculo veiculo) {
		try {
			veiculo = veiculoRepository.save(veiculo);
			return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
