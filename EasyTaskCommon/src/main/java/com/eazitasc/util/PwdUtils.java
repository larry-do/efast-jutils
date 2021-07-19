package com.eazitasc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PwdUtils {

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

    public static String generateRandomPwd(int length) {
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
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

    public static BCryptPasswordEncoder getPwdEncoder() {
        return pwdEncoder;
    }

    public static void main(String[] args) {
        System.out.println(PwdUtils.encryptRawPwd("123456"));
        System.out.println(PwdUtils.generateRandomPwd(8));
    }
}
