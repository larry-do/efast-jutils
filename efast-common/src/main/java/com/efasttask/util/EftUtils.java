package com.efasttask.util;

public class EftUtils {
    public static <T> T getValueOrElseIfNull(T orgValue, T altValue) {
        return orgValue == null ? altValue : orgValue;
    }
}
