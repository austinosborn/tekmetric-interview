package com.interview.app.config;

import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

  @ExceptionHandler(Throwable.class)
  ResponseEntity<String> defaultExceptionHandler(Throwable exception) throws Throwable {
    if (hasAnnotation(exception.getClass(), ResponseStatus.class)) {
      ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
      return ResponseEntity.status(responseStatus.value()).body(exception.getMessage());
    } else {
      String errorId = UUID.randomUUID().toString();
      log.error(String.format("Internal Server Error. ID: %s", errorId), exception);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected server error. ID: " + errorId);
    }
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<String> handleRequestValidationException(MethodArgumentNotValidException exception) {
    String message = String.join(System.lineSeparator(),
        exception.getBindingResult().getAllErrors().stream().map(ObjectError::toString).toList()
    );

    return ResponseEntity.badRequest().body("Validation failed for the following reasons:" + System.lineSeparator() + message);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exception.getMessage());
  }

  @ExceptionHandler(NoResourceFoundException.class)
  ResponseEntity<String> handleEndpointNotFound(NoResourceFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

}
