package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.impl.*;
import com.arraias.validadorjwt.validator.JwtValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static com.arraias.validadorjwt.utils.JwtUtils.gerarJwt;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {
		CompleteJwtValidator.class,
		DefaultClaimsValidator.class,
		NamePortuguesValidator.class,
		DefaultStringRoleValidator.class,
		IntegerLimitSeedValidator.class})
class CompleteJwtValidatorTest {

	@Autowired
	@Qualifier("completeJwtValidator")
	private JwtValidator jwtValidator;

	@Test
	void testJwtValidoLancaExcecao() {

		String jwt = gerarJwt(Map.of(
				"Role", "Admin",
				"Seed", "7841",
				"Name", "Toninho Araujo"));

		assertThrows(UnsupportedOperationException.class, () -> jwtValidator.validarJwt(jwt));

	}
}
