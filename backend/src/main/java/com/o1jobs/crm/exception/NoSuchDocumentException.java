package com.o1jobs.crm.exception;

public class NoSuchDocumentException extends RuntimeException {
    public NoSuchDocumentException(String message) {
        super(message);
    }
}