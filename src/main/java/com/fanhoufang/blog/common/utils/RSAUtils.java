package com.fanhoufang.blog.common.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class RSAUtils {

    private static Cipher cipher;
    //密钥长度
    private static final int KEY_SIZE = 2048;
    //公钥字符串
    private static String publicKeyString;
    //公钥
    private static PublicKey publicKey;
    //私钥
    private static PrivateKey privateKey;

    static{
        Map<String, String> keyMap = new HashMap<>();
        try {
            //创建密钥对生成器
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(KEY_SIZE);
            //生成密钥对
            KeyPair keyPair = kpg.generateKeyPair();
            //公钥
            publicKey = keyPair.getPublic();
            publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
            //私钥
            privateKey = keyPair.getPrivate();
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            keyMap.put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
            keyMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
            log.info("公钥== {}",keyMap.get("publicKey"));
            log.info("私钥== {}",keyMap.get("privateKey"));
    
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成公私钥信息失败："+e.getMessage());
        }
    }
    /**
     * 获取公钥字符串
     *
     * @param Key key
     */
    public static String getPublicKeyString(){
        return publicKeyString;
    }

    /**
     * 解密
     *
     * @param Key key
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public static String decrypt(String str) throws Exception{
                // 解密密文
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(str));
        
                // 将解密后的明文转换为字符串
                String decryptedText = new String(decryptedBytes);
                return decryptedText;
    }


    /**
     * 加密
     *
     * @param Key key
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public static String encryption(String str) throws Exception{
        // 加密明文
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(str.getBytes());

        // 将加密后的密文进行Base64编码
        String encryptedText = Base64.encodeBase64String(encryptedBytes);
        log.info("密文==:{}" , encryptedText);
        return encryptedText;
}

    /**
     * key转key字符串
     *
     * @param Key key
     */
    public static String keyToString(Key key){
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * privatekey字符串转privatekey
     *
     * @param String privatekey字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key StringToPrivateKey(String str) throws InvalidKeySpecException, NoSuchAlgorithmException{
        byte[] privateKeyBytes = Base64.decodeBase64(str);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
        return privateKey;
    }


    /**
     * publicKey字符串转publicKey
     *
     * @param String publicKey字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key StringTopublicKey(String str) throws InvalidKeySpecException, NoSuchAlgorithmException{
        byte[] publicKeyBytes = Base64.decodeBase64(str);
        PKCS8EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(publicKeyBytes);
        PrivateKey publicKey = KeyFactory.getInstance("RSA").generatePrivate(publicKeySpec);
        return publicKey;
    }

    public static void main(String[] args) throws Exception {
        String plainText = "fanhoufang";

        // 加密明文
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // 将加密后的密文进行Base64编码
        String encryptedText = Base64.encodeBase64String(encryptedBytes);
        log.info("密文==:{}" , encryptedText);

        String decrypt = decrypt(encryptedText);
        log.info("解密后明文=={}",decrypt);
    }

}