package com.arraias.validadorjwt.validator.impl;

import com.arraias.validadorjwt.validator.AbstractClaimsValidator;
import com.arraias.validadorjwt.validator.AbstractJwtValidator;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Component("basicJwtValidator")
public class BasicJwtValidator extends AbstractJwtValidator {

	@Value("${config.jwt.tamanhomaximo}")
	private int jwtTamanhoMaximo;

	private static final Logger logger = LoggerFactory.getLogger(BasicJwtValidator.class);

	@Autowired
	public BasicJwtValidator(AbstractClaimsValidator claimsValidator) {
		super(claimsValidator);
	}

	@Override
	public boolean validarJwt(String jwt) {

		if (isBlank(jwt)) {
			logger.info("Token JWT vazio");
			return false;
		}

		if (jwt.length() > jwtTamanhoMaximo) {
			logger.info("Token JWT com tamanho ({}) maior que o permitido ({})", jwt.length(), jwtTamanhoMaximo);
			return false;
		}

		try {
			Map<String, Object> claims = extrairClaims(jwt);
			return getClaimsValidator().validarClaims(claims);
		} catch (InvalidJwtException ex) {
			var exEscaped = escapeHtml4(getStackTrace(ex));
			logger.error("Falha ao extrair claims do Token JWT: %s%n%s".formatted(escapeHtml4(jwt), exEscaped));
			return false;
		}
	}

	private Map<String, Object> extrairClaims(String jwt) throws InvalidJwtException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();
		JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
		return new CaseInsensitiveMap<>(jwtClaims.getClaimsMap());
	}
}
