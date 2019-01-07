package com.cadu.pontoeletronico.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class SenhaUtilsTest {

	private final String SENHA = "123456";

	private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() throws Exception {
		assertNull(SenhaUtils.gerarBCrypt(null));
	}
	
	@Test
	public void testGerarHashSenha() {
		String hash = SenhaUtils.gerarBCrypt(SENHA);
		assertTrue(bCryptEncoder.matches(SENHA, hash));
	}
	
}
