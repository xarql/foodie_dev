package com.imooc.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * 加密操作MD5
 * @author XARQL
 * @version 2021-09-24
 */
public class MD5Utils {
    public static String getMD5Str(String strValue) throws Exception{
        /**
         * MD5 加密方法
         */
       MessageDigest  md5=MessageDigest.getInstance("MD5");
       String newstr= Base64.encodeBase64String(md5.digest(strValue.getBytes()));
       return newstr;
    }
}
