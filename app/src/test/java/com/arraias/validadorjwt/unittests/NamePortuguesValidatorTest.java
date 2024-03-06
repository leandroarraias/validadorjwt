package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.NamePortuguesValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {NamePortuguesValidator.class})
class NamePortuguesValidatorTest {

	@Autowired
	@Qualifier("namePortuguesValidator")
	private ClaimValidator ruleValidator;

	@Value("${config.name.tamanhomaximo}")
	private int nameTamanhoMaximo;

	@Test
	void testNamesValidos() {
		assertTrue(ruleValidator.validar("Toninho Araujo"));
		assertTrue(ruleValidator.validar("João da Silva"));
		assertTrue(ruleValidator.validar("João da Silva Com Sobrenome Extenso ".concat(randomAlphabetic(nameTamanhoMaximo - 36))));
		assertTrue(ruleValidator.validar("João D'Angelo da Silva côm açéntos exagerádos"));
	}

	@Test
	void testNamesInvalidos() {
		assertFalse(ruleValidator.validar(null));
		assertFalse(ruleValidator.validar(" "));
		assertFalse(ruleValidator.validar("Jo@o da Silva"));
		assertFalse(ruleValidator.validar(new BigInteger("1")));
		assertFalse(ruleValidator.validar("M4ria Olivia"));
		assertFalse(ruleValidator.validar("João da Silva Com Sobrenome Mais que Extenso ".concat(randomAlphabetic(nameTamanhoMaximo - 44))));
		assertFalse(ruleValidator.validar("<script>alert('João Não Confiável')</script>"));
		assertFalse(ruleValidator.validar("El Niño"));
	}

}
