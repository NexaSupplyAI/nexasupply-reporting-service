package com.nexasupplyai.reporting.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler({
         SnapshotPanelNoEncontradoException.class,
         ExportacionReporteNoEncontradaException.class
 })
 public ProblemDetail noEncontrado(RuntimeException e) {
  return ProblemDetail.forStatusAndDetail(
          HttpStatus.NOT_FOUND,
          e.getMessage()
  );
 }

 @ExceptionHandler(IllegalArgumentException.class)
 public ProblemDetail invalido(IllegalArgumentException e) {
  return ProblemDetail.forStatusAndDetail(
          HttpStatus.BAD_REQUEST,
          e.getMessage()
  );
 }
}