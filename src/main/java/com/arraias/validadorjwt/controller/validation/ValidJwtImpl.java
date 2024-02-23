package com.arraias.validadorjwt.controller.validation;

import com.arraias.validadorjwt.validator.JwtValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidJwtImpl implements ConstraintValidator<ValidJwt, String> {

	private final JwtValidator jwtValidator;

	@Autowired
	public ValidJwtImpl(JwtValidator jwtValidator) {
		this.jwtValidator = jwtValidator;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return jwtValidator.validarJwt(value);
	}
}
