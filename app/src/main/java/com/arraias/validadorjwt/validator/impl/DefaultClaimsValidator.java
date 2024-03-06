package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.AbstractClaimsValidator;
import com.arraias.validadorjwt.validator.ClaimValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("defaultClaimsValidator")
public class DefaultClaimsValidator extends AbstractClaimsValidator {

	private static final Logger logger = LoggerFactory.getLogger(DefaultClaimsValidator.class);

	public DefaultClaimsValidator(List<ClaimValidator> claimRules) {
		super(claimRules);
	}

	@Override
	public boolean validarClaims(Map<String, Object> claims) {

		if (claims == null) {
			logger.info("Nenhum Claim informado");
			return false;
		}

		if (claims.size() != getClaimsValidators().size()) {
			logger.info(
					"Numero de claims informados ({}) diferente de {}: {}",
					claims.size(), getClaimsValidators().size(), escapeHtml4(claims.keySet().toString()));
			return false;
		}

		for (String claim : claims.keySet().stream().toList()) {

			ClaimValidator crv = getClaimValidator(claim);

			if (crv == null) {
				logger.info("Claim {} nao permitido", escapeHtml4(claim));
				return false;
			} else if (!crv.validar(claims.get(claim))) {
				logger.info("Claim {} com valor reprovado", claim);
				return false;
			}

		}

		return true;

	}
}
