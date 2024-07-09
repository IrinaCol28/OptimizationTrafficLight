package org.example;

public class Event {
    private String type;
    private String state;
    private int length;
    private int senderId;

    public Event(String type, String state, int length, int senderId) {
        this.type = type;
        this.state = state;
        this.length = length;
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public int getLength() {
        return length;
    }

    public int getSenderId() {
        return senderId;
    }
}
