package com.ntapia.notifications.impl;

public class ConectionJmsException extends RuntimeException {

    public ConectionJmsException() {
        super();
    }

    public ConectionJmsException(String s) {
        super(s);
    }

    public ConectionJmsException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
