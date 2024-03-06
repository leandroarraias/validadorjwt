package com.arraias.validadorjwt.validator;

public interface ClaimValidator {

	String getClaimKey();

	boolean validar(Object claimValue);

}
