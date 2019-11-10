package com.ea.shop.message;

public class MetaMessage {

    private String message;
    private String severity;

    public enum SEVERITY {

        ERROR((short) 1, "ERROR"),
        INFO((short) 2, "INFO"),
        WARNING((short) 3, "WARNING");

        private final short id;
        private final String description;

        private SEVERITY(short id, String description) {
            this.id = id;
            this.description = description;
        }

        public short getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }
    }

    public MetaMessage() {

    }

    public MetaMessage(String message, String severity) {
        this.message = message;
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
