package com.cadu.pontoeletronico.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadu.pontoeletronico.api.entities.Empresa;
import com.cadu.pontoeletronico.api.repositories.EmpresaRepository;
import com.cadu.pontoeletronico.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	public final static Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional <Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscando CNPJ {}",cnpj);
		
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Persistindo Empresa {}",empresa);
		return this.empresaRepository.save(empresa);
	}
	
}
