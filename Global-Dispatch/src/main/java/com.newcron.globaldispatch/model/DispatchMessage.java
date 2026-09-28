package com.newcron.globaldispatch.model;

public class DispatchMessage {

    private String id;
    private String destination;
    private String message;

    public DispatchMessage() {
    }

    public DispatchMessage(String id, String destination, String message) {
        this.id = id;
        this.destination = destination;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
