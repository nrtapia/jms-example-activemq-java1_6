package com.ntapia.notifications.impl;

public class PublisherException extends RuntimeException {

    public PublisherException() {
        super();
    }

    public PublisherException(String s) {
        super(s);
    }

    public PublisherException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
