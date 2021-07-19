package com.eazitasc.util;

public class EasyUtils {
    public static <T> T getValueOrElseIfNull(T orgValue, T altValue) {
        return orgValue == null ? altValue : orgValue;
    }
}
