package com.efasttask.util;

import java.text.DecimalFormat;

public class EftNumber {
    public static String withoutSciNotaion(double number) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        return df.format(number);
    }
}
