package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.enums.ClaimsEnum;
import com.arraias.validadorjwt.enums.RolesEnum;
import com.arraias.validadorjwt.validator.ClaimsValidator;
import com.arraias.validadorjwt.validator.NomePortuguesValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigInteger.ONE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {ClaimsValidator.class, NomePortuguesValidator.class})
class ClaimsValidatorTest {

	@Autowired
	private ClaimsValidator claimsValidator;

	@Value("${constraints.seed.valormaximo}")
	private int seedValorMaximo;

	@Test
	void testRolesValidas() {
		assertTrue(claimsValidator.validarRole("Admin"));
		assertTrue(claimsValidator.validarRole("Member".toUpperCase()));
		assertTrue(claimsValidator.validarRole("External".toLowerCase()));
	}

	@Test
	void testRolesInvalidas() {
		assertFalse(claimsValidator.validarRole(RolesEnum.ADMIN.toString().concat("a")));
		assertFalse(claimsValidator.validarRole(null));
		assertFalse(claimsValidator.validarRole(new BigInteger("1")));
	}

	@Test
	void testSeedsValidos() {
		assertTrue(claimsValidator.validarSeed("2"));
		assertTrue(claimsValidator.validarSeed("3"));
		assertTrue(claimsValidator.validarSeed(Integer.toString(seedValorMaximo)));
	}

	@Test
	void testSeedsInvalidos() {
		assertFalse(claimsValidator.validarSeed("-1"));
		assertFalse(claimsValidator.validarSeed("0"));
		assertFalse(claimsValidator.validarSeed("1"));
		assertFalse(claimsValidator.validarSeed(null));
		assertFalse(claimsValidator.validarSeed(" "));
		assertFalse(claimsValidator.validarSeed(new BigInteger("2")));
		assertFalse(claimsValidator.validarSeed(new BigInteger(Integer.toString(seedValorMaximo)).add(ONE).toString()));
		assertFalse(claimsValidator.validarSeed("<script>alert('se passando por seed')</script>"));
	}

	@Test
	void testNamesValidos() {
		assertTrue(claimsValidator.validarName("João da Silva"));
	}

	@Test
	void testNamesInvalidos() {
		assertFalse(claimsValidator.validarName(null));
		assertFalse(claimsValidator.validarName(" "));
		assertFalse(claimsValidator.validarName("Jo@o da Silva"));
		assertFalse(claimsValidator.validarName(new BigInteger("1")));
	}

	@Test
	void testClaimsValidos() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimsEnum.NAME.name(), "Toninho Araujo");
		claims.put(ClaimsEnum.SEED.name(), "7841");
		claims.put(ClaimsEnum.ROLE.name(), "Admin");
		assertTrue(claimsValidator.validarClaims(claims));
	}

	@Test
	void testClaimsInalidos() {

		assertFalse(claimsValidator.validarClaims(null));
		assertFalse(claimsValidator.validarClaims(new HashMap<String, Object>()));

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
