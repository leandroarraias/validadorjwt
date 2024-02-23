package com.arraias.validadorjwt.controller;

import com.arraias.validadorjwt.controller.validation.ValidJwt;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/jwt")
public class ValidadorJwtController {

	private static final Logger logger = LoggerFactory.getLogger(ValidadorJwtController.class);

	@PostMapping(path = "/validar", consumes = TEXT_PLAIN_VALUE)
	public ResponseEntity<Boolean> validar(@RequestBody @Valid @ValidJwt String jwt) {
		logger.info("Token JWT validado com sucesso!");
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

}
