package com.o1jobs.crm.excpetion;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String s) {
        super(s);
    }
}
