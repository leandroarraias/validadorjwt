package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.SeedValidator;
import org.apache.commons.math3.primes.Primes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("integerLimitSeedValidator")
public class IntegerLimitSeedValidator implements SeedValidator {

	@Value("${constraints.seed.valormaximo}")
	private int seedValorMaximo;

	private static final Logger logger = LoggerFactory.getLogger(IntegerLimitSeedValidator.class);

	@Override
	public boolean validarSeed(Object seed) {

		if (seed instanceof String seedString) {

			if (!isNumeric(seedString)) {
				logger.info("Seed com valor invalido: {}", escapeHtml4(seedString));
				return false;
			}

			BigInteger bigSeed = new BigInteger(seedString);
			BigInteger bigSeedValorMaximo = new BigInteger(Integer.toString(seedValorMaximo));

			if (bigSeed.compareTo(bigSeedValorMaximo) > 0) {
				logger.info("Seed com valor ({}) maior que o permitido ({})", bigSeed, bigSeedValorMaximo);
				return false;
			}

			if (Primes.isPrime(bigSeed.intValue())) {
				return true;
			}

			logger.info("Seed ({}) nao eh primo", bigSeed);
			return false;

		} else {
			logger.info("Seed nao eh String");
			return false;
		}
	}
}
