package com.eazitasc.enumtype;

public enum VoucherStatus {
    DRAFT("D"), OUTSTANDING("O"), HISTORY("H"), COMPLETED("C"), DO_VARIATION("DV"), UNDER_VARIATION("UV");

    public static final VoucherStatus D = DRAFT;
    public static final VoucherStatus O = OUTSTANDING;
    public static final VoucherStatus H = HISTORY;
    public static final VoucherStatus C = COMPLETED;
    public static final VoucherStatus DV = DO_VARIATION;
    public static final VoucherStatus UV = UNDER_VARIATION;

    private String value;

    VoucherStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}