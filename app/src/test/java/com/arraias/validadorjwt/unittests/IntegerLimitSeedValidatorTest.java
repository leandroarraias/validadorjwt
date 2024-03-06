package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.IntegerLimitSeedValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {IntegerLimitSeedValidator.class})
class IntegerLimitSeedValidatorTest {

	@Autowired
	@Qualifier("integerLimitSeedValidator")
	private ClaimValidator ruleValidator;

	@Value("${config.seed.valormaximo}")
	private int seedValorMaximo;

	@Test
	void testSeedsValidos() {
		assertTrue(ruleValidator.validar("2"));
		assertTrue(ruleValidator.validar("3"));
		assertTrue(ruleValidator.validar(Integer.toString(seedValorMaximo)));
	}

	@Test
	void testSeedsInvalidos() {
		assertFalse(ruleValidator.validar("-1"));
		assertFalse(ruleValidator.validar("0"));
		assertFalse(ruleValidator.validar("1"));
		assertFalse(ruleValidator.validar(null));
		assertFalse(ruleValidator.validar(" "));
		assertFalse(ruleValidator.validar(new BigInteger("2")));
		assertFalse(ruleValidator.validar(new BigInteger(Integer.toString(seedValorMaximo)).add(ONE).toString()));
		assertFalse(ruleValidator.validar("<script>alert('se passando por seed')</script>"));
	}

}
