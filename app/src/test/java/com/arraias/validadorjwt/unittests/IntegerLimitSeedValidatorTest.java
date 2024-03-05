package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.SeedValidator;
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
	private SeedValidator seedValidator;

	@Value("${constraints.seed.valormaximo}")
	private int seedValorMaximo;

	@Test
	void testSeedsValidos() {
		assertTrue(seedValidator.validarSeed("2"));
		assertTrue(seedValidator.validarSeed("3"));
		assertTrue(seedValidator.validarSeed(Integer.toString(seedValorMaximo)));
	}

	@Test
	void testSeedsInvalidos() {
		assertFalse(seedValidator.validarSeed("-1"));
		assertFalse(seedValidator.validarSeed("0"));
		assertFalse(seedValidator.validarSeed("1"));
		assertFalse(seedValidator.validarSeed(null));
		assertFalse(seedValidator.validarSeed(" "));
		assertFalse(seedValidator.validarSeed(new BigInteger("2")));
		assertFalse(seedValidator.validarSeed(new BigInteger(Integer.toString(seedValorMaximo)).add(ONE).toString()));
		assertFalse(seedValidator.validarSeed("<script>alert('se passando por seed')</script>"));
	}

}
