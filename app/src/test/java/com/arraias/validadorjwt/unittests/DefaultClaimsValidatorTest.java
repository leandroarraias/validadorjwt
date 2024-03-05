package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.enums.ClaimsEnum;
import com.arraias.validadorjwt.validator.ClaimsValidator;
import com.arraias.validadorjwt.validator.impl.DefaultClaimsValidator;
import com.arraias.validadorjwt.validator.impl.DefaultStringRoleValidator;
import com.arraias.validadorjwt.validator.impl.IntegerLimitSeedValidator;
import com.arraias.validadorjwt.validator.impl.NamePortuguesValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
		DefaultClaimsValidator.class,
		NamePortuguesValidator.class,
		DefaultStringRoleValidator.class,
		IntegerLimitSeedValidator.class})
class DefaultClaimsValidatorTest {

	@Autowired
	@Qualifier("defaultClaimsValidator")
	private ClaimsValidator claimsValidator;

	@Test
	void testClaimsValidos() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimsEnum.NAME.name(), "Toninho Araujo");
		claims.put(ClaimsEnum.SEED.name(), "7841");
		claims.put(ClaimsEnum.ROLE.name(), "Admin");
		assertTrue(claimsValidator.validarClaims(claims));
	}

	@Test
	void testClaimsInvalidos() {

		assertFalse(claimsValidator.validarClaims(null));
		assertFalse(claimsValidator.validarClaims(new HashMap<>()));

		Map<String, Object> claimsInvalidos = new HashMap<>();
		claimsInvalidos.put(ClaimsEnum.NAME.name(), "Toninho Araujo");
		claimsInvalidos.put(ClaimsEnum.SEED.name(), "7841");

		// Somente 2 claims validos
		assertFalse(claimsValidator.validarClaims(claimsInvalidos));

		// 2 claims validos e 1 invalido
		claimsInvalidos.put(null, "Nao importa o valor");
		assertFalse(claimsValidator.validarClaims(claimsInvalidos));

		// 2 claims validos e 1 invalido
		claimsInvalidos.remove(null);
		claimsInvalidos.put("claim_invalido", "Nao importa o valor");
		assertFalse(claimsValidator.validarClaims(claimsInvalidos));

		// 3 claims validos e 1 invalido
		claimsInvalidos.put(ClaimsEnum.ROLE.name(), "Admin");
		assertFalse(claimsValidator.validarClaims(claimsInvalidos));

	}
}
