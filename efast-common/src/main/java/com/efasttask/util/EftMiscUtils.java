package com.efasttask.util;

public class EftMiscUtils {
    public static <T> T getValueOrElseIfNull(T orgValue, T altValue) {
        return orgValue == null ? altValue : orgValue;
    }

    /**
     * Get the method name for a depth in call stack. <br />
     * Utility function
     * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
     * @return method name
     */
    public static String getMethodName(final int depth) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[ste.length - 1 - depth].getMethodName();
    }
}
