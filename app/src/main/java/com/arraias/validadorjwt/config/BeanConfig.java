package com.arraias.validadorjwt.config;

import com.arraias.validadorjwt.validator.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Value("${constraints.jwt.validador}")
	private String constraintsJwtValidador;

	@Value("${constraints.claims.validador}")
	private String constraintsClaimsValidador;

	@Value("${constraints.name.validador}")
	private String constraintsNameValidador;

	@Value("${constraints.seed.validador}")
	private String constraintsSeedValidador;

	@Value("${constraints.role.validador}")
	private String constraintsRoleValidador;

	@Bean
	public JwtValidator jwtValidator(ApplicationContext context) {
		return (JwtValidator) context.getBean(constraintsJwtValidador);
	}

	@Bean
	public ClaimsValidator claimsValidator(ApplicationContext context) {
		return (ClaimsValidator) context.getBean(constraintsClaimsValidador);
	}

	@Bean
	public NameValidator nameValidator(ApplicationContext context) {
		return (NameValidator) context.getBean(constraintsNameValidador);
	}

	@Bean
	public SeedValidator seedValidator(ApplicationContext context) {
		return (SeedValidator) context.getBean(constraintsSeedValidador);
	}

	@Bean
	public RoleValidator roleValidator(ApplicationContext context) {
		return (RoleValidator) context.getBean(constraintsRoleValidador);
	}
}
