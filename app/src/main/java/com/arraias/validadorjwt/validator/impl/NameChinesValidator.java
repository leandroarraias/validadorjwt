package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.enums.ClaimsEnum;
import org.springframework.stereotype.Component;

@Component("nameChinesValidator")
public class NameChinesValidator implements ClaimValidator {

	@Override
	public String getClaimKey() {
		return ClaimsEnum.NAME.name();
	}

	@Override
	public boolean validar(Object claimValue) {
		throw new UnsupportedOperationException("O validador de name Chines ainda nao foi implementado.");
	}
}
