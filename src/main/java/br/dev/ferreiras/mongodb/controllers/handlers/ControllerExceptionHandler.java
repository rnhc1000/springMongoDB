package br.dev.ferreiras.mongodb.controllers.handlers;


import br.dev.ferreiras.mongodb.models.dto.ExceptionDTO;
import br.dev.ferreiras.mongodb.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionDTO> resourceNotFoundException(ResourceNotFoundException exception, ServerHttpRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND;
    return ResponseEntity.ok().body(new ExceptionDTO(
        Instant.now(),
        status.value(),
        exception.getMessage(),
        request.getURI().getPath())
    );
  }
}
