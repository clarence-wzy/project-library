package com.ripen.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.util.Scanner;

/**
 * 对称加密算法 - 3ES加密算法
 *
 * @author Ripen.Y
 * @version 2020/08/03 16:26
 * @since 2020/08/03
 */
public class ThreeDes {

    /**
     * 加密规则KEY
     */
    public static final String KEY = "qwertyuiopasdfghjkklzxcvbnm";

    /**
     * 加密，key必须是长度大于等于 3*8 = 24 位
     *
     * @param content 加密字符串
     * @return 返回加密后的字符串
     * @throws Exception 异常声明
     */
    public static String encryptThreeDESECB(final String content)
    {
        try {
            final DESedeKeySpec dks = new DESedeKeySpec(KEY.getBytes("UTF-8"));
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            final SecretKey securekey = keyFactory.generateSecret(dks);

            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            final byte[] b = cipher.doFinal(content.getBytes());

            final BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密，key必须是长度大于等于 3*8 = 24 位
     *
     * @param content 加密字符串
     * @return 返回加密后的字符串
     */
    public static String decryptThreeDESECB(final String content)
    {
        try {
            // 通过base64,将字符串转成byte数组
            final BASE64Decoder decoder = new BASE64Decoder();
            final byte[] bytesrc = decoder.decodeBuffer(content);

            // 解密的key
            final DESedeKeySpec dks = new DESedeKeySpec(KEY.getBytes("UTF-8"));
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            final SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象解密
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            final byte[] retByte = cipher.doFinal(bytesrc);

            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 加密
        System.out.println("请输入要加密的内容:");
        String content = scanner.next();
        System.out.println("根据输入的规则" + ThreeDes.KEY + "加密后的密文是:" + ThreeDes.encryptThreeDESECB(content));

        // 解密
        System.out.println("请输入要解密的内容（密文）:");
        content = scanner.next();
        System.out.println("根据输入的规则" + ThreeDes.KEY + "解密后的明文是:" + ThreeDes.decryptThreeDESECB(content));
    }

}