package com.eazitasc.enumtype;

public enum GenderEnum {
    MALE("M"), FEMALE("F"), OTHER("O");

    public static final GenderEnum M = MALE;
    public static final GenderEnum F = FEMALE;
    public static final GenderEnum O = OTHER;

    private String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}