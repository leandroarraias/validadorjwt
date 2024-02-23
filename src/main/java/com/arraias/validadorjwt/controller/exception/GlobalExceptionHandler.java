package com.arraias.validadorjwt.controller.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(HandlerMethodValidationException.class)
	public final ResponseEntity<Boolean> handleException(HandlerMethodValidationException ex) {
		return new ResponseEntity<>(Boolean.FALSE, FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Boolean> handleException(Exception ex) {
		var st = escapeHtml4(getStackTrace(ex));
		logger.error("Erro ao realizar operacao.\n{}", st);
		return new ResponseEntity<>(Boolean.FALSE, INTERNAL_SERVER_ERROR);
    }
}
