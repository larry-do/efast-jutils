package com.efasttask.utils.enumtype;

public enum BooleanLegacy {
    YES("Y"), NO("N"), TRUE("T"), FALSE("F");

    public static final BooleanLegacy Y = YES;
    public static final BooleanLegacy N = NO;
    public static final BooleanLegacy T = TRUE;
    public static final BooleanLegacy F = FALSE;

    private String value;

    BooleanLegacy(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}