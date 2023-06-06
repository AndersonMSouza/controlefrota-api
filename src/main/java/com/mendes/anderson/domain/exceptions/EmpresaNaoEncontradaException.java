package com.mendes.anderson.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmpresaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EmpresaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EmpresaNaoEncontradaException(Long empresaId) {
		this(String.format("Não existe um cadastro de empresa com código %d", empresaId));
	}
	
}