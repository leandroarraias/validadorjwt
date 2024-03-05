package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.enums.RolesEnum;
import com.arraias.validadorjwt.validator.RoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("defaultStringRoleValidator")
public class DefaultStringRoleValidator implements RoleValidator {

	private static final Logger logger = LoggerFactory.getLogger(DefaultStringRoleValidator.class);

	@Override
	public boolean validarRole(Object role) {
		if (role instanceof String roleString) {

			if (RolesEnum.get(roleString.toUpperCase()) != null) {
				return true;
			}

			logger.info("Role com valor invalido: {}", escapeHtml4(roleString));
			return false;

		} else {
			logger.info("Role nao eh String");
			return false;
		}
	}
}
