package ies.healthmanager.websockets;

public enum MessageType {
    CONNECT("connect"),
    DISCONNECT("disconnect"),
    NOTIFICATION("notification");

    private final String type;

    public String getType() {
        return this.type;
    }

    private MessageType(String type)
    {
        this.type = type;
    }
}