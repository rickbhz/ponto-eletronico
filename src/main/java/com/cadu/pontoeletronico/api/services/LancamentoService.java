package com.cadu.pontoeletronico.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cadu.pontoeletronico.api.entities.Lancamento;

public interface LancamentoService {

	Page<Lancamento> buscarPorFuncionarioId(Long funcionario, PageRequest pageRequest);
	
	Optional<Lancamento> buscarPorid (Long id);
	
	Lancamento persistir(Lancamento lancamento);
	
	void remover (Long id);
} 
