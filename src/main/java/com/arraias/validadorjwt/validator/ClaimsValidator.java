package com.arraias.validadorjwt.validator;

import com.arraias.validadorjwt.enums.ClaimsEnum;
import com.arraias.validadorjwt.enums.RolesEnum;
import org.apache.commons.math3.primes.Primes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Map;

import static com.arraias.validadorjwt.enums.ClaimsEnum.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component
public class ClaimsValidator {

	@Value("${constraints.seed.valormaximo}")
	private int seedValorMaximo;

	private final NomeValidator nomeValidator;

	private static final Logger logger = LoggerFactory.getLogger(ClaimsValidator.class);

	@Autowired
	public ClaimsValidator(NomeValidator nomeValidator) {
		this.nomeValidator = nomeValidator;
	}

	public boolean validarClaims(Map<String, Object> claims) {

		if (claims == null || claims.isEmpty()) {
			logger.info("Nenhum Claim informado");
			return false;
		}

		if (claims.size() != ClaimsEnum.values().length) {
			logger.info(
					"Numero de claims informados ({}) diferente de {}: {}",
					claims.size(), ClaimsEnum.values().length, escapeHtml4(claims.keySet().toString()));
			return false;
		}

		for (String claim : claims.keySet().stream().toList()) {
			if (claim == null || ClaimsEnum.get(claim.toUpperCase()) == null) {
				logger.info("Claim {} nao permitido", escapeHtml4(claim));
				return false;
			}
		}

		if (!validarRole(claims.get(ROLE.toString()))) return false;
		if (!validarSeed(claims.get(SEED.toString()))) return false;
		if (!validarName(claims.get(NAME.toString()))) return false;

		return true;

	}

	public boolean validarRole(Object claimRole) {

		if (claimRole instanceof String role) {

			if (RolesEnum.get(role.toUpperCase()) != null) {
				return true;
			}

			logger.info("Claim Role com valor invalido: {}", escapeHtml4(role));
			return false;

		} else {
			logger.info("Claim Role nao eh String");
			return false;
		}
	}

	public boolean validarSeed(Object claimSeed) {

		if (claimSeed instanceof String seed) {

			if (!isNumeric(seed)) {
				logger.info("Claim Seed com valor invalido: {}", escapeHtml4(seed));
				return false;
			}

			BigInteger bigSeed = new BigInteger(seed);
			BigInteger bigSeedValorMaximo = new BigInteger(Integer.toString(seedValorMaximo));

			if (bigSeed.compareTo(bigSeedValorMaximo) > 0) {
				logger.info("Claim Seed com valor ({}) maior que o permitido ({})", bigSeed, bigSeedValorMaximo);
				return false;
			}

			if (Primes.isPrime(bigSeed.intValue())) {
				return true;
			}

			logger.info("Claim Seed ({}) nao eh primo", bigSeed);
			return false;

		} else {
			logger.info("Claim Seed nao eh String");
			return false;
		}
	}

	public boolean validarName(Object claimName) {
		if (claimName instanceof String name) {
			return nomeValidator.validarNome(name);
		} else {
			logger.info("Claim Name nao eh String");
			return false;
		}
	}
}
