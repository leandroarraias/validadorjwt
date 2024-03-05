package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.NameValidator;
import org.springframework.stereotype.Component;

@Component("nameChinesValidator")
public class NameChinesValidator implements NameValidator {

	@Override
	public boolean validarName(Object name) {
		throw new UnsupportedOperationException("O validador de name Chines ainda nao foi implementado.");
	}
}
