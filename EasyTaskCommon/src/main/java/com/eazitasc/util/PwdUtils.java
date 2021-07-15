package com.eazitasc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PwdUtils {

    private static final int DEFAULT_STRENGTH = 10;
    private static BCryptPasswordEncoder passwordEncoder;

    static {
        passwordEncoder = new BCryptPasswordEncoder(DEFAULT_STRENGTH);
    }

    public static String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean checkPasswordMatch(String encryptedPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    public static String generateRandomPassword(int length) {
        char[] chars =
                {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.',
                        '/', ':', ';', '<', '=', '>', '?', '@', '^', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                stringBuilder.append(chars[random.nextInt(chars.length)]);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Can't generate random password!");
        }
        return null;
    }

    public static BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public static void main(String[] args) {
        System.out.println(PwdUtils.encryptPassword("123456"));
        System.out.println(PwdUtils.generateRandomPassword(8));
    }
}
