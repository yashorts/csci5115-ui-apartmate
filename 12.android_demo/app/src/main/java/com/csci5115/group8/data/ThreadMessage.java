package com.csci5115.group8.data;

public class ThreadMessage {
    public Boolean me;
    public String message;

    public ThreadMessage(String message, Boolean isMe) {
        this.message = message;
        this.me = isMe;
    }
}
