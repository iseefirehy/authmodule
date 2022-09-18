package com.example.authmodule.util;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 16:44
 */
public class EncryptionUtil {
    /**
     *  Using the rotating hash method for the encryption
     * @param tokenOrPassword
     * @return
     */
    public static String encryptedWithRotatingHash(String tokenOrPassword) {
        long hash = 0;
        for (int i = 0;i < tokenOrPassword.length();i++) {
            hash = (hash << 10) ^ (hash >> 22) ^ tokenOrPassword.charAt(i);
        }
        return String.valueOf(hash);
    }
}
