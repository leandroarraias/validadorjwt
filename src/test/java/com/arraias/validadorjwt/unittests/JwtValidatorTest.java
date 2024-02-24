package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.ClaimsValidator;
import com.arraias.validadorjwt.validator.JwtValidator;
import com.arraias.validadorjwt.validator.NomeValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {JwtValidator.class, ClaimsValidator.class, NomeValidator.class, ObjectMapper.class})
class JwtValidatorTest {

	@Autowired
	private JwtValidator jwtValidator;

	@Value("${constraints.jwt.tamanhomaximo}")
	private int jwtTamanhoMaximo;

	@Test
	void testJwtValidos() throws JoseException {

		String jwt = gerarJwt(Map.of(
				"Role", "Admin",
				"Seed", "7841",
				"Name", "Toninho Araujo"));

		assertTrue(jwtValidator.validarJwt(jwt));

	}

	@Test
	void testJwtInvalidos() throws JsonProcessingException, JoseException {

		assertFalse(jwtValidator.validarJwt(null));
		assertFalse(jwtValidator.validarJwt(" "));
		assertFalse(jwtValidator.validarJwt(randomAlphabetic(jwtTamanhoMaximo + 1)));

		String jwtFormatadoIncorretamente = "eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
		assertFalse(jwtValidator.validarJwt(jwtFormatadoIncorretamente));

		String jwtNomeInvalido = gerarJwt(Map.of(
				"Role", "External",
				"Seed", "72341",
				"Name", "M4ria Olivia"));
		assertFalse(jwtValidator.validarJwt(jwtNomeInvalido));

		String jwtMaisDe3Claims = gerarJwt(Map.of(
				"Role", "Member",
				"Org", "BR",
				"Seed", "14627",
				"Name", "Valdir Aranha"));
		assertFalse(jwtValidator.validarJwt(jwtMaisDe3Claims));

		String jwtSeedNaoString = gerarJwt(Map.of(
				"Role", "Admin",
				"Seed", 7841,
				"Name", "Toninho Araujo"));
		assertFalse(jwtValidator.validarJwt(jwtSeedNaoString));

		String jwtRoleInvalida = gerarJwt(Map.of(
				"Role", "AdminPower",
				"Seed", "7841",
				"Name", "Toninho Araujo"));
		assertFalse(jwtValidator.validarJwt(jwtRoleInvalida));

		String jwtClaimsExorbitantes = gerarJwt(Map.of(
				"Role", randomAlphabetic(1000),
				"Seed", randomAlphabetic(1000),
				"Name", randomAlphabetic(1000)));
		assertFalse(jwtValidator.validarJwt(jwtClaimsExorbitantes));

		String jwtClaimsScripts = gerarJwt(Map.of(
				"Role", "<script>alert('nao deve passar')</script>",
				"Seed", "<script>alert('nao deve passar')</script>",
				"Name", "<script>alert('nao deve passar')</script>"));
		assertFalse(jwtValidator.validarJwt(jwtClaimsScripts));

	}

	private String gerarJwt(Map<String, Object> claimsMap) throws JoseException {

		JwtClaims claims = new JwtClaims();

		for (String key : claimsMap.keySet()) {
			claims.setClaim(key, claimsMap.get(key));
		}

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(new HmacKey("QomFf4ivJSvqsUv5T2c8KJx7TDMKjaZzKuJ1STBBVlg=".getBytes()));
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
		return jws.getCompactSerialization();

	}
}
