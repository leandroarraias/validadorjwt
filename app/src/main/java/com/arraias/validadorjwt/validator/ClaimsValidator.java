package com.arraias.validadorjwt.validator;

import java.util.Map;

public interface ClaimsValidator {

	boolean validarClaims(Map<String, Object> claims);

}
