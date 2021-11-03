package com.eazitasc.enumtype;

public enum ApprovalStatus {
    APPROVED("A"), PENDING("P"), REJECTED("R"), NONE("N");

    public static final ApprovalStatus A = APPROVED;
    public static final ApprovalStatus P = PENDING;
    public static final ApprovalStatus R = REJECTED;
    public static final ApprovalStatus N = NONE;

    private String value;

    ApprovalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}