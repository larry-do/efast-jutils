package com.kwicktask.utils.enumtype;

public enum ProcessingStatus {
    PROCESSING("P"), FAILED("F"), SUCCESS("S");

    public static final ProcessingStatus P = PROCESSING;
    public static final ProcessingStatus F = FAILED;
    public static final ProcessingStatus S = SUCCESS;

    private String value;

    ProcessingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}