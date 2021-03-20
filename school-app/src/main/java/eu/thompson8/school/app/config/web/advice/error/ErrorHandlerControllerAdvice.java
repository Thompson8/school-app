package eu.thompson8.school.app.config.web.advice.error;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import eu.thompson8.school.app.exception.entity.EntityNotFoundException;
import eu.thompson8.school.app.model.error.ApiError;
import eu.thompson8.school.app.model.error.validation.FieldValidationErrorDetail;

@ControllerAdvice
public class ErrorHandlerControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleGenericException(HttpServletRequest request, Exception e) {
		logger.error("Error during request processing, method: {}, uri: {}, params: [{}]", request.getMethod(),
				request.getRequestURI(), getParameters(request), e);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> handleEntityNotFoundException(EntityNotFoundException e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ApiError apiError = new ApiError();
		apiError.setErrorMessage(String.format("Invalid object: %s", e.getObjectName()));
		apiError.setErrorDetails(getFieldValidationErrorDetails(e.getBindingResult().getFieldErrors()));

		return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private List<Object> getFieldValidationErrorDetails(List<FieldError> list) {
		return list.stream().map(this::mapToFieldValidationErrorDetail).collect(Collectors.toList());
	}

	private FieldValidationErrorDetail mapToFieldValidationErrorDetail(FieldError fieldError) {
		FieldValidationErrorDetail result = new FieldValidationErrorDetail();
		result.setField(fieldError.getField());
		result.setErrorMessage(fieldError.getDefaultMessage());

		return result;
	}

	private String getParameters(HttpServletRequest request) {
		return request.getParameterMap().entrySet().stream()
				.map(e -> String.format("{name: %s, value: %s}", e.getKey(), Arrays.toString(e.getValue())))
				.collect(Collectors.joining("; "));
	}

}
