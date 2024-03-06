package com.arraias.validadorjwt.config;

import com.arraias.validadorjwt.validator.AbstractClaimsValidator;
import com.arraias.validadorjwt.validator.AbstractJwtValidator;
import com.arraias.validadorjwt.validator.ClaimValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanConfig {

	@Value("${config.jwt.validador}")
	private String configJwtValidador;

	@Value("${config.claims.validador}")
	private String configClaimsValidador;

	@Value("${config.claims.rules}")
	private List<String> claimRules;

	@Bean
	public List<ClaimValidator> claimRulesValidator(ApplicationContext context) {

		if (claimRules == null || claimRules.isEmpty()) {
			return new ArrayList<>();
		}

		ArrayList<ClaimValidator> rules = new ArrayList<>();

		for (String claimRule : claimRules) {
			rules.add((ClaimValidator) context.getBean(claimRule));
		}

		return rules;

	}

	@Bean
	public AbstractJwtValidator jwtValidator(ApplicationContext context) {
		return (AbstractJwtValidator) context.getBean(configJwtValidador);
	}

	@Bean
	public AbstractClaimsValidator claimsValidator(ApplicationContext context) {
		List<ClaimValidator> rules = claimRulesValidator(context);
		return (AbstractClaimsValidator) context.getBean(configClaimsValidador, rules);
	}
}
