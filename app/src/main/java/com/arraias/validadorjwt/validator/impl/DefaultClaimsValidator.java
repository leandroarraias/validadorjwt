package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.enums.ClaimsEnum;
import com.arraias.validadorjwt.validator.ClaimsValidator;
import com.arraias.validadorjwt.validator.NameValidator;
import com.arraias.validadorjwt.validator.RoleValidator;
import com.arraias.validadorjwt.validator.SeedValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.arraias.validadorjwt.enums.ClaimsEnum.*;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("defaultClaimsValidator")
public class DefaultClaimsValidator implements ClaimsValidator {

	private final RoleValidator roleValidator;
	private final SeedValidator seedValidator;
	private final NameValidator nameValidator;

	private static final Logger logger = LoggerFactory.getLogger(DefaultClaimsValidator.class);

	@Autowired
	public DefaultClaimsValidator(
			RoleValidator roleValidator,
			SeedValidator seedValidator,
			NameValidator nameValidator) {
		this.roleValidator = roleValidator;
		this.seedValidator = seedValidator;
		this.nameValidator = nameValidator;
	}

	@Override
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

		if (!roleValidator.validarRole(claims.get(ROLE.toString()))) return false;
		if (!seedValidator.validarSeed(claims.get(SEED.toString()))) return false;
		if (!nameValidator.validarName(claims.get(NAME.toString()))) return false;

		return true;

	}
}
