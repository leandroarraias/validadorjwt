package com.arraias.validadorjwt.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidJwtImpl.class})
public @interface ValidJwt {

	String message() default "Token JWT invalido.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
