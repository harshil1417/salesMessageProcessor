package com.harshil.salesnotificationprocessor.model;

public class SalesNotification {
    private MessageTypes messageType;
    private Object message;

    public SalesNotification(MessageTypes messageType, Object message) {
        this.messageType = messageType;
        this.message = message;
    }

    public MessageTypes getMessageType() {
        return messageType;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesNotification event = (SalesNotification) o;

        if (messageType != event.messageType) return false;
        return message != null ? message.equals(event.message) : event.message == null;
    }

    @Override
    public int hashCode() {
        int result = messageType != null ? messageType.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SalesNotification{" +
                "messageType=" + messageType +
                ", message=" + message +
                '}';
    }
}
