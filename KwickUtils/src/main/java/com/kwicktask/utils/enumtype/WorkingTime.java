package com.kwicktask.utils.enumtype;

public enum WorkingTime {
    FULL_DAY("FD"), ANTE_MEDIRIEM("AM"), POST_MEDIRIEM("PM");

    public static final WorkingTime FD = FULL_DAY;
    public static final WorkingTime AM = ANTE_MEDIRIEM;
    public static final WorkingTime PM = POST_MEDIRIEM;

    private String value;

    WorkingTime(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}