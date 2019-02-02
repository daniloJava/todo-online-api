package br.com.todo.onlineservice.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.todo.onlineservice.exception.RecordNotFoundException;
import br.com.todo.onlineservice.model.ErrorResponse;
import br.com.todo.onlineservice.model.FieldError;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRecordNotFound(RecordNotFoundException ex, WebRequest request) {
		return createResponseEntity(ex, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> otherErrors(Exception ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return createResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErros = new ArrayList<>();

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			fieldErros.add(new FieldError(error.getObjectName(), error.getDefaultMessage()));
		}

		for (org.springframework.validation.FieldError error : ex.getBindingResult().getFieldErrors()) {
			fieldErros.add(new FieldError(error.getField(), error.getDefaultMessage()));
		}

		ErrorResponse error = ErrorResponse.builder().message(status.getReasonPhrase()).details(request.getDescription(false)).fieldErros(fieldErros).build();
		return new ResponseEntity<>(error, status);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErros = new ArrayList<>();

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			fieldErros.add(new FieldError(error.getObjectName(), error.getDefaultMessage()));
		}

		for (org.springframework.validation.FieldError error : ex.getBindingResult().getFieldErrors()) {
			fieldErros.add(new FieldError(error.getField(), error.getDefaultMessage()));
		}

		ErrorResponse error = ErrorResponse.builder().message(status.getReasonPhrase()).details(request.getDescription(false)).fieldErros(fieldErros).build();
		return new ResponseEntity<>(error, status);
	}

	private ResponseEntity<ErrorResponse> createResponseEntity(final Exception exception, final WebRequest request, final HttpStatus status) {
		ErrorResponse error = ErrorResponse.builder().message(exception.getLocalizedMessage()).details(request.getDescription(false)).timestamp(LocalDateTime.now()).build();
		return new ResponseEntity<>(error, status);
	}

}
