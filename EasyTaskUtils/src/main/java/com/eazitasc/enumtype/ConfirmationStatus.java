package com.eazitasc.enumtype;

public enum ConfirmationStatus {
    CONFIRMED("C"), UNCONFIRMED("UC");

    public static final ConfirmationStatus C = CONFIRMED;
    public static final ConfirmationStatus UC = UNCONFIRMED;

    private String value;

    ConfirmationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}