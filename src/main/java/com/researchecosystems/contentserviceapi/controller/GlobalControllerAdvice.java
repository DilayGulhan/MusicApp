package com.researchecosystems.contentserviceapi.controller;

import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.exception.ErrorDTO;
import com.researchecosystems.contentserviceapi.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> customHandleBusinessException(BusinessException ex, WebRequest request) {
        LOGGER.info("Business Error: {}", ex.getMessage());
        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ex.getErrorCode().getHttpCode())
                .error(ex.getErrorCode().toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(error.getStatus()));
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ErrorCode.validation.getHttpCode())
                .error(ErrorCode.validation.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ErrorCode.validation.getHttpCode())
                .error(ErrorCode.validation.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(error.getStatus()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(status.value())
                .error(ErrorCode.unknown.name())
                .message(ex.getRequestPartName() + " is missing!")
                .build();
        return new ResponseEntity<>(error, headers, status);
    }
}
