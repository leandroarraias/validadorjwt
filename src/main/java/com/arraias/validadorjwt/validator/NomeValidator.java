package com.arraias.validadorjwt.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class NomeValidator {

    private static final String ALFABETICO = "a-zA-Z";
    private static final String A_ESPECIAL = "\\u00C0-\\u00C4\\u00E0-\\u00E4";
    private static final String E_ESPECIAL = "\\u00C8-\\u00CB\\u00E8-\\u00EB";
    private static final String I_ESPECIAL = "\\u00CC-\\u00CF\\u00EC-\\u00EF";
    private static final String O_ESPECIAL = "\\u00D2-\\u00D6\\u00F2-\\u00F6";
    private static final String U_ESPECIAL = "\\u00D9-\\u00DC\\u00F9-\\u00FC";
    private static final String C_CEDILHA = "\\u00C7\\u00E7";
    private static final String N_ESPECIAL = "\\u00D1\\u00F1";
    private static final String Y_ESPECIAL = "\\u00DD\\u00FD";
    private static final String OUTROS_CARACTERES = " '-";

    private final String pattern;

    public NomeValidator() {

        pattern = "[" +
				ALFABETICO +
				A_ESPECIAL +
				E_ESPECIAL +
				I_ESPECIAL +
				O_ESPECIAL +
				U_ESPECIAL +
				C_CEDILHA +
				N_ESPECIAL +
				Y_ESPECIAL +
				OUTROS_CARACTERES +
				"]+";

    }

    public boolean validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            return false;
        }

        return Pattern.matches(pattern, nome);

    }

}
