package com.mk.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuilder;

public class PasswordHasher {

    public static String hash(String pwd){
        try{
            byte[] pwdBytes = pwd.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = md.digest(pwdBytes);
            StringBuilder sb = new StringBuilder();

            for(byte b : hashBytes){
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

    }

    public static boolean verify(String pwd, String hash){
        return hash.equals(hash(pwd));
    }


}
