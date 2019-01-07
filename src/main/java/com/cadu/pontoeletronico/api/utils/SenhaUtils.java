package com.cadu.pontoeletronico.api.utils;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SenhaUtils {


/**
* Gera um hash utilizando o BCrypt.
*
* @param senha
* @return String
*/
	
private static final Logger log = LoggerFactory.getLogger(SenhaUtils.class);	
	
public static String gerarBCrypt ( String senha ) {
if ( senha == null ) {
return senha ;
}

log.info("Gerando hash");
BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder ();
return bCryptEncoder . encode ( senha );
}
/**
* Verifica se a senha é válida.
*
* @param senha
* @param senhaEncoded
* @return boolean
*/
public static boolean senhaValida ( String senha , String senhaEncoded ) {
BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder ();
return bCryptEncoder . matches ( senha , senhaEncoded );
}
}