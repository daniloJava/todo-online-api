package br.com.todo.onlineservice.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.todo.onlineservice.security.jwt.InvalidJwtAuthenticationException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ExceptionHandler(value = { InvalidJwtAuthenticationException.class })
	public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
		log.debug("handling InvalidJwtAuthenticationException...");
		return status(UNAUTHORIZED).build();
	}
}