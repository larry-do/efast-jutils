package com.kwicktask.utils.enumtype;

public enum ActiveStatus {
    ACTIVE("A"), INACTIVE("IA"), DISABLED("D");

    public static final ActiveStatus A = ACTIVE;
    public static final ActiveStatus IA = INACTIVE;
    public static final ActiveStatus D = DISABLED;

    private String value;

    ActiveStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}