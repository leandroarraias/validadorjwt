package com.arraias.validadorjwt.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractJwtValidator {

	private final AbstractClaimsValidator claimsValidator;

	@Autowired
	protected AbstractJwtValidator(AbstractClaimsValidator claimsValidator) {
		this.claimsValidator = claimsValidator;
	}

	protected AbstractClaimsValidator getClaimsValidator() {
		return claimsValidator;
	}

	public abstract boolean validarJwt(String jwt);

}
