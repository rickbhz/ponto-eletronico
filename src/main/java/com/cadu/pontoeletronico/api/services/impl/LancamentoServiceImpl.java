package com.cadu.pontoeletronico.api.services.impl;


import java.util.Optional;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cadu.pontoeletronico.api.entities.Lancamento;
import com.cadu.pontoeletronico.api.repositories.LancamentoRepository;
import com.cadu.pontoeletronico.api.services.LancamentoService;



@Service
public class LancamentoServiceImpl implements LancamentoService {

	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionario, PageRequest pageRequest) {
		log.info("Buscando um lancamento pelo funcionario ID {}", funcionario);
		return this.lancamentoRepository.findByFuncionarioId(funcionario, pageRequest);
		
	}

	@Override
	@Cacheable("lancamentoPorId")
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando por ID {}",id);
		
		return this.lancamentoRepository.findById(id);
	}

	@Override
	@CachePut("lancamentoPorId")
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo: {}",lancamento);
		
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo: {}",id);
		this.lancamentoRepository.deleteById(id);
		
	}
	
}
