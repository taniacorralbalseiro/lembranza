// src/main/java/es/udc/lembranza/rest/common/RestExceptionHandler.java
package es.udc.lembranza.rest.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
@Slf4j
@Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSource messageSource;

    /* ===== Helpers i18n ===== */
    private String msg(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, code, locale);
    }

    private FieldErrorDto toFieldErrorDto(FieldError fe, Locale locale) {
        // Mensaje de validación ya viene localizado por Hibernate Validator según locale
        var message = fe.getDefaultMessage();
        return new FieldErrorDto(fe.getField(), message);
    }

    /* ===== 400: Validación ===== */

    //validacion body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, Locale locale) {
        List<FieldErrorDto> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> toFieldErrorDto(fe, locale))
                .toList();
        return ResponseEntity.badRequest().body(new ErrorsDto(fieldErrors));
    }

    //validacion query params
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorsDto> handleBindException(BindException ex, Locale locale) {
        List<FieldErrorDto> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> toFieldErrorDto(fe, locale))
                .toList();
        return ResponseEntity.badRequest().body(new ErrorsDto(fieldErrors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorsDto> handleConstraintViolation(ConstraintViolationException ex, Locale locale) {
        var fieldErrors = ex.getConstraintViolations().stream()
                .map(v -> new FieldErrorDto(v.getPropertyPath().toString(), v.getMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ErrorsDto(fieldErrors));
    }
/*
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorsDto> handleUnreadable(HttpMessageNotReadableException ex, Locale locale) {
        return ResponseEntity.badRequest()
                .body(new ErrorsDto(msg("project.errors.body.invalid", locale)));
    }*/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorsDto> handleUnreadable(HttpMessageNotReadableException ex, Locale locale) {
        Throwable cause = ex.getMostSpecificCause();
        // LOG visible en consola con el detalle que necesitas:
        log.warn("JSON unreadable: {}", (cause != null ? cause.getMessage() : ex.getMessage()), ex);

        // (opcional) incluir el detalle en la respuesta mientras depuras:
        String detail = (cause != null ? cause.getMessage() : ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorsDto(msg("project.errors.body.invalid", locale) + " :: " + detail));
        // Cuando acabes de depurar, vuelve a la versión “limpia” sin el detail.
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorsDto> handleMissingParam(MissingServletRequestParameterException ex, Locale locale) {
        var field = ex.getParameterName();
        return ResponseEntity.badRequest()
                .body(new ErrorsDto(List.of(new FieldErrorDto(field, msg("project.errors.param.required", locale)))));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorsDto> handleTypeMismatch(TypeMismatchException ex, Locale locale) {
        var field = ex.getPropertyName() != null ? ex.getPropertyName() : "param";
        return ResponseEntity.badRequest()
                .body(new ErrorsDto(List.of(new FieldErrorDto(field, msg("project.errors.param.type", locale)))));
    }

    /* ===== 401/403 ===== */

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorsDto> handleBadCredentials(BadCredentialsException ex, Locale locale) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorsDto(msg("project.errors.bad.credentials", locale)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorsDto> handleAccessDenied(AccessDeniedException ex, Locale locale) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorsDto(msg("project.errors.access.denied", locale)));
    }

    /* ===== 404/409 de dominio (ajusta a tus paquetes) ===== */

    @ExceptionHandler(es.udc.lembranza.model.exceptions.InstanceNotFoundException.class)
    public ResponseEntity<ErrorsDto> handleInstanceNotFound(
            es.udc.lembranza.model.exceptions.InstanceNotFoundException ex, Locale locale) {

        // ex.getName() debería ser un code de entidad: p.ej. "project.entities.paciente"
        String entity = msg(ex.getName(), locale);
        String error  = msg("project.exceptions.InstanceNotFound", locale, entity, ex.getKey().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorsDto(error));
    }

    @ExceptionHandler(es.udc.lembranza.model.exceptions.DuplicateInstanceException.class)
    public ResponseEntity<ErrorsDto> handleDuplicate(
            es.udc.lembranza.model.exceptions.DuplicateInstanceException ex, Locale locale) {

        String entity = msg(ex.getName(), locale);
        String error  = msg("project.exceptions.DuplicateInstance", locale, entity, ex.getKey().toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorsDto(error));
    }

    // Opcional: violaciones DB (unique, FK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorsDto> handleDataIntegrity(DataIntegrityViolationException ex, Locale locale) {
        // Puedes mapear a 409 o 400 según el caso
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorsDto(msg("project.exceptions.DuplicateInstance", locale, msg("project.entities.usuario", locale), "?")));
    }

    /* ===== 405 ===== */

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorsDto> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, Locale locale) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorsDto(msg("project.errors.method.not.allowed", locale)));
    }

    /* ===== 500 genérico ===== */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorsDto> handleGeneric(Exception ex, Locale locale) {
        // log.error("Unhandled", ex); // añade logging si quieres
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorsDto(msg("project.errors.unexpected", locale)));
    }
}
