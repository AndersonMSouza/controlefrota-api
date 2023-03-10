package com.mendes.anderson.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.anderson.domain.model.Empresa;
import com.mendes.anderson.domain.repository.EmpresaRepository;


@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping
	public List<Empresa> listar() {
		return empresaRepository.findAll();
	}

}
