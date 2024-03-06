package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.enums.ClaimsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("namePortuguesValidator")
public class NamePortuguesValidator implements ClaimValidator {

	@Value("${config.name.tamanhomaximo}")
	private int nameTamanhoMaximo;

    protected static final String ALFABETICO = "a-zA-Z";
	protected static final String A_ESPECIAL = "\\u00C0-\\u00C4\\u00E0-\\u00E4";
	protected static final String E_ESPECIAL = "\\u00C8-\\u00CB\\u00E8-\\u00EB";
	protected static final String I_ESPECIAL = "\\u00CC-\\u00CF\\u00EC-\\u00EF";
	protected static final String O_ESPECIAL = "\\u00D2-\\u00D6\\u00F2-\\u00F6";
	protected static final String U_ESPECIAL = "\\u00D9-\\u00DC\\u00F9-\\u00FC";
	protected static final String C_CEDILHA = "\\u00C7\\u00E7";
	protected static final String OUTROS_CARACTERES = " '";

    private final String pattern;

	private static final Logger logger = LoggerFactory.getLogger(NamePortuguesValidator.class);

	public NamePortuguesValidator() {

        pattern = "[" +
				ALFABETICO +
				A_ESPECIAL +
				E_ESPECIAL +
				I_ESPECIAL +
				O_ESPECIAL +
				U_ESPECIAL +
				C_CEDILHA +
				OUTROS_CARACTERES +
				"]+";

    }

	@Override
	public String getClaimKey() {
		return ClaimsEnum.NAME.name();
	}

	@Override
	public boolean validar(Object claimValue) {

		if (claimValue instanceof String name) {

			if (name.isBlank()) {
				logger.info("Name nao informado: \"{}\"", name);
				return false;
			}

			if (name.length() > nameTamanhoMaximo) {
				logger.info("Name com tamanho invalido: {}", name.length());
				return false;
			}

			if (!Pattern.matches(getPattern(), name)) {
				logger.info("Name com caracteres invalidos: {}", escapeHtml4(name));
				return false;
			}

			return true;

		} else {
			logger.info("Name nao eh String");
			return false;
		}
    }

	protected String getPattern() {
		return pattern;
	}

}
