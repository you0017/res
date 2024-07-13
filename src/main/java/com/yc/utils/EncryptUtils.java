package com.yc.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptUtils {
    public static String encryptToMD5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            //digest()最后确定返回MD5 hash值，返回值为8位字符串
            //BigInteger 函数夫 8 位字符串转为16位hex值，用字符串表示
            String hashedPwd = new BigInteger(1,md.digest()).toString(16);
            return hashedPwd;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtils.encryptToMD5(EncryptUtils.encryptToMD5("a")));
    }
}
