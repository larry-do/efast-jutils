package com.efasttask.utils.enumtype;

public enum Gender {
    MALE("M"), FEMALE("FE"), OTHER("O");

    public static final Gender M = MALE;
    public static final Gender FE = FEMALE;
    public static final Gender O = OTHER;

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}