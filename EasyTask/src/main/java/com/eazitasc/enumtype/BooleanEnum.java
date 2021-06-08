package com.eazitasc.enumtype;

public enum BooleanEnum {
    YES("Y"), NO("N");

    public static final BooleanEnum Y = YES;
    public static final BooleanEnum N = NO;

    private String value;

    BooleanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}