package com.wzy.util;

import java.security.MessageDigest;

/**
 * @Package: com.wzy.util
 * @Author: Clarence1
 * @Date: 2019/10/4 15:31
 */
public class Md5Util {

    public String Md5Util(String password) {
        String result;
        result = getString(password);
        return result;
    }

    /**
     * @decription 32位小写的Md5加密
     * @param password
     * @return
     */
    public static String getString(String password) {
        String result;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update((password).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        result = buf.toString();
        return result;
    }

}
