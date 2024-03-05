package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.NameValidator;
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
	private NameValidator nameValidator;

	@Value("${constraints.name.tamanhomaximo}")
	private int nameTamanhoMaximo;

	@Test
	void testNamesValidos() {
		assertTrue(nameValidator.validarName("Toninho Araujo"));
		assertTrue(nameValidator.validarName("João da Silva"));
		assertTrue(nameValidator.validarName("João da Silva Com Sobrenome Extenso ".concat(randomAlphabetic(nameTamanhoMaximo - 36))));
		assertTrue(nameValidator.validarName("João D'Angelo da Silva côm açéntos exagerádos"));
	}

	@Test
	void testNamesInvalidos() {
		assertFalse(nameValidator.validarName(null));
		assertFalse(nameValidator.validarName(" "));
		assertFalse(nameValidator.validarName("Jo@o da Silva"));
		assertFalse(nameValidator.validarName(new BigInteger("1")));
		assertFalse(nameValidator.validarName("M4ria Olivia"));
		assertFalse(nameValidator.validarName("João da Silva Com Sobrenome Mais que Extenso ".concat(randomAlphabetic(nameTamanhoMaximo - 44))));
		assertFalse(nameValidator.validarName("<script>alert('João Não Confiável')</script>"));
		assertFalse(nameValidator.validarName("El Niño"));
	}

}
