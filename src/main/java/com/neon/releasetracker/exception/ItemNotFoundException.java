package com.neon.releasetracker.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Integer id) {
        super("Could not find item " + id);
    }
}
