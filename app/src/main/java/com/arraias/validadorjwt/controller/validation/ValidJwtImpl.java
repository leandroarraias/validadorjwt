package com.arraias.validadorjwt.controller.validation;

import com.arraias.validadorjwt.validator.AbstractJwtValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidJwtImpl implements ConstraintValidator<ValidJwt, String> {

	private final AbstractJwtValidator jwtValidator;

	@Autowired
	public ValidJwtImpl(AbstractJwtValidator jwtValidator) {
		this.jwtValidator = jwtValidator;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return jwtValidator.validarJwt(value);
	}
}
