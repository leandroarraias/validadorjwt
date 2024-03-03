package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.NomePortuguesValidator;
import com.arraias.validadorjwt.validator.NomeValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {NomePortuguesValidator.class})
class NomePortuguesValidatorTest {

	@Autowired
	private NomeValidator nomeValidator;

	@Value("${constraints.name.tamanhomaximo}")
	private int nameTamanhoMaximo;

	@Test
	void testNomesValidos() {
		assertTrue(nomeValidator.validarNome("João da Silva"));
		assertTrue(nomeValidator.validarNome("João da Silva Com Sobrenome Extenso ".concat(randomAlphabetic(nameTamanhoMaximo - 36))));
		assertTrue(nomeValidator.validarNome("João D'Angelo da Silva côm açéntos exagerádos"));
	}

	@Test
	void testNomesInvalidos() {
		assertFalse(nomeValidator.validarNome(null));
		assertFalse(nomeValidator.validarNome(" "));
		assertFalse(nomeValidator.validarNome("Jo@o da Silva"));
		assertFalse(nomeValidator.validarNome("M4ria Olivia"));
		assertFalse(nomeValidator.validarNome("João da Silva Com Sobrenome Mais que Extenso ".concat(randomAlphabetic(nameTamanhoMaximo - 44))));
		assertFalse(nomeValidator.validarNome("<script>alert('João Não Confiável')</script>"));
	}

}
