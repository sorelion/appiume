package com.cmit.clouddetection.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AES {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return Base64Utils.encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64Utils.decodeToBytes(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
//    	String ip = "{\"data\":{\"ip\":\"192.168.118.27\",\"channel\":11}}";
//    	ip = new Base64().encodeBase64String(ip.getBytes());
//    	System.out.println(ip);
//        /*
//         * 此处使用AES-128-ECB加密模式，key需要为16位。
//         */
//        String cKey = "MTAuMS4xLjI=MTAu";
//        // 需要加密的字串
//        String cSrc = "孟华是个婊子";
//        System.out.println(cSrc);
//        // 加密
//        String enString = AES.Encrypt(cSrc, cKey);
//        System.out.println("加密后的字串是：" + enString);
//
//        // 解密
//        String DeString = AES.Decrypt(enString, cKey);
//        System.out.println("解密后的字串是：" + DeString);
//
//        cKey = "MTkyLjE2OC4xMTgu";
//        String info = "lXJc1GTdEHrW1eQSDJD8npa6DI+DVPxifR893QYJLFkUu7uuUQjT79589sUqqAZV";
//        info = AES.Decrypt(info, cKey);
//        System.out.println(info);

        String cKey = "20171207173114ok";
        String info = "20171207173114";
        info = AES.Encrypt(info, cKey);
        System.out.println(info);
        String skey = info.substring(0, 16);
        System.out.println(skey);
        info = AES.Encrypt("FA-16-3E-02-BB-E9", skey);
        System.out.println(info);
        System.out.println(AES.Decrypt(info, skey));
    }
}
