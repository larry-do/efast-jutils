package com.efasttask.util.message;

public class Message {
    protected final String message;
    protected final Object[] values;

    public Message(String message, Object... values) {
        this.message = message;
        this.values = values;
    }

    /**
     * By default, system parse message by String.format
     * May re-implement by your own way
     */
    public String getParsedMessage() {
        if (values != null && values.length > 0) {
            return String.format(message, values);
        }
        return this.message;
    }

    public void show() {
        throw new UnsupportedOperationException("Method has not be implemented yet!");
    }
}
