package com.arraias.validadorjwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/observability")
public class ObservatilityController {

	private static final Logger logger = LoggerFactory.getLogger(ObservatilityController.class);

	@GetMapping(value = "logs")
	public ResponseEntity<Boolean> gerarLogs(
			@RequestParam("log") String log, @RequestParam("qtde") int qtde, @RequestParam("espera") long espera) {

		try {
			Thread.sleep(espera * 1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		if ("debug".equalsIgnoreCase(log)) {
			for (int i = 0; i < qtde; ) {
				logger.debug("Log do tipo debug {}", ++i);
			}
		} else if ("info".equalsIgnoreCase(log)) {
			for (int i = 0; i < qtde; ) {
				logger.info("Log do tipo info {}", ++i);
			}
		} else if ("warn".equalsIgnoreCase(log)) {
			for (int i = 0; i < qtde; ) {
				logger.warn("Log do tipo warn {}", ++i);
			}
		} else {
			for (int i = 0; i < qtde; ) {
				logger.error("Log do tipo error {}", ++i);
			}
		}

		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

	}

	@GetMapping(value = "status")
	public ResponseEntity<Boolean> gerarLogs(
			@RequestParam("status") int status, @RequestParam("espera") long espera) {

		try {
			Thread.sleep(espera * 1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.valueOf(status));

	}

}
