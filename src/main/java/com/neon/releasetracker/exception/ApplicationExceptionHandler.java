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

        ApiException apiException = new ApiException(
                e.getMessage(),
                ZonedDateTime.now()
        );
        log.error("Custom - Exception (NOT_FOUND): {}", apiException);
        return new ResponseEntity<>(
                apiException,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReleaseTrackerException.class)
    public ResponseEntity<Object> handleApiRequestException(ReleaseTrackerException e) {

        ApiException apiException = new ApiException(
                e.getMessage(),
                ZonedDateTime.now()
        );
        log.error("Custom - Exception (INTERNAL_SERVER_ERROR): {}", apiException);
        return new ResponseEntity<>(
                apiException,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleApiRequestNotReadableException(HttpMessageNotReadableException e) {

        ApiException apiException = new ApiException(
                e.getMessage(),
                ZonedDateTime.now()
        );
        log.error("Custom - Exception (BAD_REQUEST): {}", apiException);
        return new ResponseEntity<>(
                apiException,
                HttpStatus.BAD_REQUEST);
    }
}
