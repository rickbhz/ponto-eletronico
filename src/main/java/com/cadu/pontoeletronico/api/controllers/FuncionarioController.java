package com.cadu.pontoeletronico.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadu.pontoeletronico.api.dtos.FuncionarioDto;
import com.cadu.pontoeletronico.api.entities.Funcionario;
import com.cadu.pontoeletronico.api.response.Response;
import com.cadu.pontoeletronico.api.services.FuncionarioService;
import com.cadu.pontoeletronico.api.utils.SenhaUtils;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando  Funcionario: {}",funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();

		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
		
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionario não encontrado"));
		}
		
		this.atualizarDadosFuncionario(funcionario.get(),funcionarioDto,result);
		
		
		if (result.hasErrors()) {
			log.info("Validando Funcionario {}"+result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

        this.funcionarioService.persistir(funcionario.get());
        response.setData(this.converterFuncionarioDto(funcionario.get()));
        
        return ResponseEntity.ok(response);
		
	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setEmail(funcionario.getEmail());
		
		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHorasTrabalhoDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabalhoDia))));
		funcionario.getValorHoraOpt().ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));
		
		return funcionarioDto;
	}

	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto,
			BindingResult result) throws NoSuchAlgorithmException {
		
		funcionario.setNome(funcionarioDto.getNome());
		
		if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			
			this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
			    .ifPresent(func -> result.addError(new ObjectError("email","Email já existe")));
			funcionario.setEmail(funcionarioDto.getEmail());
		}
		
		funcionario.setQtdHorasAlmoco(null);
		funcionarioDto.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		
		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getQtdHorasTrabalhoDia().ifPresent(qtdTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdTrabalhoDia)));
		
		funcionario.setValorHora(null);
		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		
		if (funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(SenhaUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
		}
		

	}

}
