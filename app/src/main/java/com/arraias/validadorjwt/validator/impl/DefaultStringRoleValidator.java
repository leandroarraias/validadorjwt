package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.enums.ClaimsEnum;
import com.arraias.validadorjwt.validator.impl.enums.RolesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("defaultStringRoleValidator")
public class DefaultStringRoleValidator implements ClaimValidator {

	private static final Logger logger = LoggerFactory.getLogger(DefaultStringRoleValidator.class);

	@Override
	public String getClaimKey() {
		return ClaimsEnum.ROLE.name();
	}

	@Override
	public boolean validar(Object claimValue) {
		if (claimValue instanceof String role) {

			if (RolesEnum.get(role.toUpperCase()) != null) {
				return true;
			}

			logger.info("Role com valor invalido: {}", escapeHtml4(role));
			return false;

		} else {
			logger.info("Role nao eh String");
			return false;
		}
	}
}
