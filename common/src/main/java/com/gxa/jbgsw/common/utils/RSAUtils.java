package com.gxa.jbgsw.common.utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RSAUtils {

    public static final String PUBLIC_KEY = "35";
    public static final String PRIVATE_KEY = "7";


    public static void main(String[] args) {

    }


    public static Map<String, String> generateRasKey() {
        Map<String, String> rs = new HashMap<>();
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥 公钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            rs.put(PUBLIC_KEY, publicKeyString);
            rs.put(PRIVATE_KEY, privateKeyString);

            log.info(rs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }


}
