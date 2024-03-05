package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.ClaimsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("completeJwtValidator")
public class CompleteJwtValidator extends BasicJwtValidator {

	private static final Logger logger = LoggerFactory.getLogger(CompleteJwtValidator.class);

	public CompleteJwtValidator(ClaimsValidator claimsValidator) {
		super(claimsValidator);
	}

	@Override
	public boolean validarJwt(String jwt) {

		if (!validarTokenAuthServer(jwt)) {
			logger.info("Token nao reconhecido pelo Auth Server");
			return false;
		}

		return super.validarJwt(jwt);

	}

	private boolean validarTokenAuthServer(String jwt) {

		//
		// Aqui teria uma validação se o Authentication Server realmente emitiu o token
		//
		throw new UnsupportedOperationException("O validacao do token no auth server ainda nao foi implementada.");

	}
}
