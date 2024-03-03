package com.arraias.validadorjwt.config;

import com.arraias.validadorjwt.validator.NomeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Value("${constraints.name.validador}")
	private String constraintsNameValidador;

	@Bean
	public NomeValidator nomeValidator(ApplicationContext context) {
		return (NomeValidator) context.getBean(constraintsNameValidador);
	}
}
