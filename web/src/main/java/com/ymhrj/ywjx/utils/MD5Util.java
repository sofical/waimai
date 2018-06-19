package com.ymhrj.ywjx.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @author Mir
 */
public final class MD5Util {
    final static char[]  HEX = "0123456789abcdef".toCharArray();
    static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5Util init error.");
        }
    }
    public static String toHexString(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX[(b[i] >>> 4) & 0x0F]);
            sb.append(HEX[b[i] & 0x0F]);
        }
        return sb.toString();
    }
    public static byte[] encode(byte[] b) {
        if (b == null) {
            return null;
        }
        return md5.digest(b);
    }
    public static String encode(String s) {
        if (s == null) {
            return null;
        }
        return toHexString(encode(s.getBytes()));
    }
    
    public static String encode(String s, String charset) {
        if (s == null) {
            return null;
        }
        try {
            return toHexString(encode(s.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5Util init error.");
        }
    }
    
    /**
     * 获取99u登录密码加密
     * @author zxh
     * @param password
     * @return
     */
    public static String getMD5(String password) {
        String value = "";
        byte[] data_key = "fdjf,jkgfkl".getBytes();
        byte[] data_pwd = password.getBytes();
        int size_key = data_key.length;
        int size_pwd = data_pwd.length;
        
        byte[] data = new byte[size_pwd + 4 + size_key];
        byte[] data_fk = new byte[]{(byte)-93, (byte)-84, (byte)-95, (byte)-93};
        System.arraycopy(data_pwd, 0, data, 0, size_pwd);
        System.arraycopy(data_fk, 0, data, size_pwd, data_fk.length);
        System.arraycopy(data_key, 0, data, size_pwd + data_fk.length, size_key);
        
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] buff = md.digest(data);
            value = Md5toHexString(buff);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return value;
    }
    
    public static String Md5toHexString(byte[] b) {    
        StringBuilder sb = new StringBuilder(b.length * 2);    
        for (int i = 0; i < b.length; i++) {    
            sb.append(HEX[(b[i] & 0xf0) >>> 4]);    
            sb.append(HEX[b[i] & 0x0f]);    
        }    
        return sb.toString();    
    }    
}
