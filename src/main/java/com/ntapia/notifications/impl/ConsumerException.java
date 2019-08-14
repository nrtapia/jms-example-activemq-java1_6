package com.ntapia.notifications.impl;

public class ConsumerException extends RuntimeException {

    public ConsumerException() {
        super();
    }

    public ConsumerException(String s) {
        super(s);
    }

    public ConsumerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
