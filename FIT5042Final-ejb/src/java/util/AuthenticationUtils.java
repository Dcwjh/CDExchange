/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Shicheng Ai
 */
public class AuthenticationUtils {
    
    public static String encodeMD5(String str) throws UnsupportedEncodingException {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }
    
     /**
	 * Returns SHA-256 encoded string
	 * @param password - the string to be encoded
	 * @return SHA-256 encoded string
	 * @throws UnsupportedEncodingException if UTF-8 is not supported by the system
	 * @throws NoSuchAlgorithmException if SHA-256 is not supported by the system
	 */
    public static String encodeSHA256(String password) 
                    throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return DatatypeConverter.printBase64Binary(digest).toString();
    }

}
