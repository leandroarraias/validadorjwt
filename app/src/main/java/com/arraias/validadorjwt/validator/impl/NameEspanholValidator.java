package com.arraias.validadorjwt.validator.impl;

import org.springframework.stereotype.Component;

@Component("nameEspanholValidator")
public class NameEspanholValidator extends NamePortuguesValidator {

	private static final String N_ESPECIAL = "\\u00D1\\u00F1";

	private final String patternEspanhol;

	public NameEspanholValidator() {

		patternEspanhol = "[" +
				ALFABETICO +
				A_ESPECIAL +
				E_ESPECIAL +
				I_ESPECIAL +
				O_ESPECIAL +
				U_ESPECIAL +
				C_CEDILHA +
				N_ESPECIAL +
				OUTROS_CARACTERES +
				"]+";

	}

	@Override
	public String getPattern() {
		return this.patternEspanhol;
	}
}
