package com.arraias.validadorjwt.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.arraias.validadorjwt.utils.JwtUtils.gerarJwt;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class ValidadorJwtApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@Value("${constraints.jwt.tamanhomaximo}")
	private int jwtTamanhoMaximo;

	@Test
	void testJwtValidos() {

		String jwt = gerarJwt(Map.of(
				"Role", "Admin",
				"Seed", "7841",
				"Name", "Toninho Araujo"));

		ResponseEntity<Boolean> response = executar(jwt);
		assertEquals(OK, response.getStatusCode());
		assertEquals(TRUE, response.getBody());

	}

	@Test
	void testJwtInvalidos() {

		validarFalseForbidden(executar(" "));

		validarFalseForbidden(executar(randomAlphabetic(jwtTamanhoMaximo + 1)));

		String jwtFormatadoIncorretamente = "eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
		validarFalseForbidden(executar(jwtFormatadoIncorretamente));

		String jwtNameInvalido = gerarJwt(Map.of(
				"Role", "External",
				"Seed", "72341",
				"Name", "M4ria Olivia"));
		validarFalseForbidden(executar(jwtNameInvalido));

		String jwtMaisDe3Claims = gerarJwt(Map.of(
				"Role", "Member",
				"Org", "BR",
				"Seed", "14627",
				"Name", "Valdir Aranha"));
		validarFalseForbidden(executar(jwtMaisDe3Claims));

		String jwtSeedNaoString = gerarJwt(Map.of(
				"Role", "Admin",
				"Seed", 7841,
				"Name", "Toninho Araujo"));
		validarFalseForbidden(executar(jwtSeedNaoString));

		String jwtRoleInvalida = gerarJwt(Map.of(
				"Role", "AdminPower",
				"Seed", "7841",
				"Name", "Toninho Araujo"));
		validarFalseForbidden(executar(jwtRoleInvalida));

		String jwtClaimsExorbitantes = gerarJwt(Map.of(
				"Role", randomAlphabetic(1000),
				"Seed", randomAlphabetic(1000),
				"Name", randomAlphabetic(1000)));
		validarFalseForbidden(executar(jwtClaimsExorbitantes));

		String jwtClaimsScripts = gerarJwt(Map.of(
				"Role", "<script>alert('nao deve passar')</script>",
				"Seed", "<script>alert('nao deve passar')</script>",
				"Name", "<script>alert('nao deve passar')</script>"));
		validarFalseForbidden(executar(jwtClaimsScripts));

	}

	private ResponseEntity<Boolean> executar(String body) {
		HttpEntity<String> request = new HttpEntity<>(body);
		return template.postForEntity("http://localhost:8080/jwt/validar", request, Boolean.class);
	}

	private void validarFalseForbidden(ResponseEntity<Boolean> response) {
		assertEquals(FORBIDDEN, response.getStatusCode());
		assertEquals(FALSE, response.getBody());
	}
}
