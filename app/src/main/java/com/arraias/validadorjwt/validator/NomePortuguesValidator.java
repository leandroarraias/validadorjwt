package com.arraias.validadorjwt.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("nomePortuguesValidator")
public class NomePortuguesValidator implements NomeValidator {

	@Value("${constraints.name.tamanhomaximo}")
	private int nameTamanhoMaximo;

    private static final String ALFABETICO = "a-zA-Z";
    private static final String A_ESPECIAL = "\\u00C0-\\u00C4\\u00E0-\\u00E4";
    private static final String E_ESPECIAL = "\\u00C8-\\u00CB\\u00E8-\\u00EB";
    private static final String I_ESPECIAL = "\\u00CC-\\u00CF\\u00EC-\\u00EF";
    private static final String O_ESPECIAL = "\\u00D2-\\u00D6\\u00F2-\\u00F6";
    private static final String U_ESPECIAL = "\\u00D9-\\u00DC\\u00F9-\\u00FC";
    private static final String C_CEDILHA = "\\u00C7\\u00E7";
    private static final String N_ESPECIAL = "\\u00D1\\u00F1";
    private static final String Y_ESPECIAL = "\\u00DD\\u00FD";
    private static final String OUTROS_CARACTERES = " '";

    private final String pattern;

	private static final Logger logger = LoggerFactory.getLogger(NomePortuguesValidator.class);

    public NomePortuguesValidator() {

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

	@Override
	public boolean validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
			logger.info("Nome nao informado: \"{}\"", nome);
            return false;
        }

		if (nome.length() > nameTamanhoMaximo) {
			logger.info("Nome com tamanho invalido: {}", nome.length());
			return false;
		}

        if (!Pattern.matches(pattern, nome)) {
			logger.info("Nome com caracteres invalidos: {}", escapeHtml4(nome));
			return false;
		}

		return true;
    }

}
