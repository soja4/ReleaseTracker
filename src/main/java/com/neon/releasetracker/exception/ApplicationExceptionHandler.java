package com.neon.releasetracker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {

        ApiExceptionOverride apiExceptionOverride = new ApiExceptionOverride(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        log.error("Custom - Exception (NOT_FOUND): {}", apiExceptionOverride);
        return new ResponseEntity<>(
                apiExceptionOverride,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReleaseTrackerException.class)
    public ResponseEntity<Object> handleApiRequestException(ReleaseTrackerException e) {

        ApiExceptionOverride apiExceptionOverride = new ApiExceptionOverride(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        log.error("Custom - Exception (INTERNAL_SERVER_ERROR): {}", apiExceptionOverride);
        return new ResponseEntity<>(
                apiExceptionOverride,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleApiRequestNotReadableException(HttpMessageNotReadableException e) {

        ApiExceptionOverride apiExceptionOverride = new ApiExceptionOverride(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        log.error("Custom - Exception (BAD_REQUEST): {}", apiExceptionOverride);
        return new ResponseEntity<>(
                apiExceptionOverride,
                HttpStatus.BAD_REQUEST);
    }
}
