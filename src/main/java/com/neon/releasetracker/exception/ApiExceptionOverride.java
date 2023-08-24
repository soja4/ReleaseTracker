package com.neon.releasetracker.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiExceptionOverride(String message, HttpStatus httpStatus,
                                   ZonedDateTime zonedDateTime) {
}
