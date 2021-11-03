package com.eazitasc.enumtype;

public enum Periodicity {
    HOURLY("H"), DAILY("D"), WEEKLY("W"), MONTHLY("M"), QUARTERLY("Q"), YEARLY("Y");

    public static final Periodicity H = HOURLY;
    public static final Periodicity D = DAILY;
    public static final Periodicity W = WEEKLY;
    public static final Periodicity M = MONTHLY;
    public static final Periodicity Q = QUARTERLY;
    public static final Periodicity Y = YEARLY;

    private String value;

    Periodicity(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}