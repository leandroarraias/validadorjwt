package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.NameChinesValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {NameChinesValidator.class})
class NameChinesValidatorTest {

	@Autowired
	@Qualifier("nameChinesValidator")
	private ClaimValidator ruleValidator;

	@Test
	void testQualquerNameLancaExcecao() {
		assertThrows(UnsupportedOperationException.class, () -> ruleValidator.validar("Qualquer name"));
	}
}
