package com.cadu.pontoeletronico.api.services;

import java.util.Optional;

import com.cadu.pontoeletronico.api.entities.Empresa;

public interface EmpresaService {

	
	Optional <Empresa> buscarPorCnpj (String cnpj);
	
	Empresa persistir (Empresa empresa);
}
