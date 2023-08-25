package com.neon.releasetracker.exception;

import java.time.ZonedDateTime;

public record ApiException(String message, ZonedDateTime zonedDateTime) {}
