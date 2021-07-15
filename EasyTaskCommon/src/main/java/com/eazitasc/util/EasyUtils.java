package com.eazitasc.util;

public class EasyUtils {
    public static <T> T getValueOrElseNull(T orgValue, T altValue) {
        return orgValue == null ? altValue : orgValue;
    }
}
