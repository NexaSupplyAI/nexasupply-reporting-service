package com.nexasupplyai.reporting.exception;
import com.nexasupplyai.reporting.config.CorrelationIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
 private static final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
 @ExceptionHandler({ExportacionReporteNoEncontradaException.class,SnapshotPanelNoEncontradoException.class})
 ResponseEntity<ApiError> notFound(RuntimeException e,HttpServletRequest r){return response(HttpStatus.NOT_FOUND,"RESOURCE_NOT_FOUND",e.getMessage(),r,Map.of());}
 @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class})
 ResponseEntity<ApiError> invalid(RuntimeException e,HttpServletRequest r){return response(HttpStatus.BAD_REQUEST,"INVALID_REQUEST",e.getMessage(),r,Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){var fields=e.getBindingResult().getFieldErrors().stream()
  .collect(Collectors.toMap(x->x.getField(),x->x.getDefaultMessage()==null?"Valor inválido":x.getDefaultMessage(),(a,b)->a));
  return response(HttpStatus.BAD_REQUEST,"VALIDATION_ERROR","La solicitud contiene campos inválidos",r,fields);}
 @ExceptionHandler(DataIntegrityViolationException.class)
 ResponseEntity<ApiError> conflict(DataIntegrityViolationException e,HttpServletRequest r){return response(HttpStatus.CONFLICT,"DATA_CONFLICT","La operación infringe una restricción de datos",r,Map.of());}
 @ExceptionHandler(RestClientException.class)
 ResponseEntity<ApiError> dependency(RestClientException e,HttpServletRequest r){log.warn("Dependencia no disponible: {}",e.getMessage());return response(HttpStatus.BAD_GATEWAY,"DEPENDENCY_UNAVAILABLE","No fue posible consultar un microservicio de origen",r,Map.of());}
 @ExceptionHandler(Exception.class)
 ResponseEntity<ApiError> unexpected(Exception e,HttpServletRequest r){log.error("Error inesperado",e);return response(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_ERROR","Ocurrió un error interno",r,Map.of());}
 private ResponseEntity<ApiError> response(HttpStatus s,String c,String m,HttpServletRequest r,Map<String,String> f){return ResponseEntity.status(s)
  .body(new ApiError(s.value(),c,m,r.getRequestURI(),MDC.get(CorrelationIdFilter.MDC_KEY),OffsetDateTime.now(ZoneOffset.UTC),f));}
}
