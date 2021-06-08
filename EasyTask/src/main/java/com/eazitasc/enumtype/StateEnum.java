package com.eazitasc.enumtype;

public enum StateEnum {
    ACTIVE("A"), INACTIVE("IA");

    public static final StateEnum A = ACTIVE;
    public static final StateEnum IA = INACTIVE;

    private String value;

    StateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}