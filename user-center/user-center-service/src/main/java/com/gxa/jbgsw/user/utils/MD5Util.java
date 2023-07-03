package com.gxa.jbgsw.user.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFromPass(String inputPass){
        String str = salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(4)+salt.charAt(6);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt){
        String str = salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(4)+salt.charAt(6);
        return md5(str);
    }


    public static String inputPassToDPass(String inputPass, String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, salt);
        return dbPass;
    }


    public static void main(String[] args) {
        // 3a5c4fa256b1672bc2201077c97daed0
        System.out.println(inputPassToDPass("44da0618ab06d9fd03ad7aff84a82027", "c0ae9feb"));

    }


}
