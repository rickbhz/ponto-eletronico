package com.cadu.pontoeletronico.api.services;

import java.util.Optional;

import com.cadu.pontoeletronico.api.entities.Funcionario;


public interface FuncionarioService {

	Funcionario persistir (Funcionario funcionario);
	
	Optional <Funcionario> buscarPorCpf(String cpf);
	
	Optional <Funcionario> buscarPorEmail (String email);
	
	Optional <Funcionario> buscarPorId (Long id);
	
}