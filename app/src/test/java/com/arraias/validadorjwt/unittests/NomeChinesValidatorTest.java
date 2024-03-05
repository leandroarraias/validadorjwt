package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.NameValidator;
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
	private NameValidator nameValidator;

	@Test
	void testQualquerNameLancaExcecao() {
		assertThrows(UnsupportedOperationException.class, () -> nameValidator.validarName("Qualquer name"));
	}
}
