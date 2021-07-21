package com.eazitasc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PwdUtils {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";

    private static final int DEFAULT_STRENGTH = 12;
    private static BCryptPasswordEncoder pwdEncoder;

    static {
        pwdEncoder = new BCryptPasswordEncoder(DEFAULT_STRENGTH);
    }

    public static String encryptRawPwd(String rawPwd) {
        return pwdEncoder.encode(rawPwd);
    }

    public static boolean checkPwdMatch(String rawPwd, String encryptedPwd) {
        return pwdEncoder.matches(rawPwd, encryptedPwd);
    }

    public static String generateRandomPwd(boolean lower, boolean upper, boolean digit, boolean punctuation, int length) {
        if (length <= 0) {
            return "";
        }

        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        List<String> charCategories = new ArrayList<>(4);
        if (lower) charCategories.add(LOWER);
        if (upper) charCategories.add(UPPER);
        if (digit) charCategories.add(DIGITS);
        if (punctuation) charCategories.add(PUNCTUATION);

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }

    public static BCryptPasswordEncoder getPwdEncoder() {
        return pwdEncoder;
    }

    public static void main(String[] args) {
        System.out.println(PwdUtils.encryptRawPwd("123456"));
        System.out.println(PwdUtils.generateRandomPwd(true, true, true, true, 12));
    }
}
