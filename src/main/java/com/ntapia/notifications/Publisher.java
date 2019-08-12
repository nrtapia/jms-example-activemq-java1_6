package com.ntapia.notifications;

public interface Publisher {

    void send(String target, String message);
}
