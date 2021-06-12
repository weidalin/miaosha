package com.imooc.miaosha.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 第一层， 用户向服务器
     * @param inputPass
     * @return
     */
    public static String inputPassFormPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2)
                + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密， 服务器向数据库存储的值
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2)
                + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }



    public static void main(String[] args) {
        System.out.println(inputPassFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassFormPass("123456"), "1a2b3c4d"));
        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }
}
