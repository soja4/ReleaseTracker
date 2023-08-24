package com.neon.releasetracker.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Integer id) {
        super("Could not find item " + id);
    }
}
