package com.heihei.management.system.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RSAUtil
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/24 9:31
 **/
public class RSAUtil {
    private static Map<Integer,String> keyMap = new HashMap<>();
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdI7nKKyhxF5IqIj39xFz8Dess7zcTs7eCAZshLejTal+QlSnGcdugBnh54pfoo" +
            "fM9mJ1rPcLfDjX84/SrhuE4Zg8V5ecE++56Fcwk+1jZLS0SJGxAgAmOnwA2cQgUWzgonpPcmSufNevVVIgLM3A/1Yibhm1lu4rZjIPzDcU+aQIDAQAB";
    public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ0jucorKHEXkioiPf3EXPwN6yzvNxOzt4IBmyEt6NNqX" +
            "5CVKcZx26AGeHnil+ih8z2YnWs9wt8ONfzj9KuG4ThmDxXl5wT77noVzCT7WNktLRIkbECACY6fADZxCBRbOCiek9yZK58169VUiAszcD/ViJuGbWW7itmMg/MNxT" +
            "5pAgMBAAECgYBdFjfOOojHjeYJTaWZecR3kCs7wC3JXxIbUqY6Hn8pFn+sH6DSrMok3xBpa8D/j0iPuaOvTrPs/Pcga02aetlZaJBK3FNZfASDdlpiyOAqphEPnsR" +
            "FiakjxBT4W6MZMIHVhy1CO8gquf6b3AxoPnmY8hDF7xRKKHH+cm6ZQr2CHQJBANVAlBjlBl60l8YbudWAbAVxhwXPOJtMl6+cq6Ldr1LpWpdMVAXVTjzKzg8Y89v3+SIW" +
            "MN9P/0MCQOzAhNtH9ysCQQC8o5+OOzL0hJP5n/ctSldks/FNQqGbfLkFthwYG6h8QdB32PZMx0Z3wi/8Knp2wnFrV9RZdkr2WL54kyT34ha7AkEA1BIytUcdPJVv0g3Ekv" +
            "PxeMBbvIVFD/vtZTDy5zZ6ooN6GbhIXryv33j3zr/L01cZJLLYy1tsyKC3HDqgZMl+nQJAGUB6CmsoTg01VdQxHXH3MvbXlXUW9x6CEh6nsh55tnCE/JYhmnOOnldzXvk8vYs" +
            "X7burJHKuZD3K+Y8RAtHUIwJBAJI/FwY8cZOuiCz2R2BUR724QGlv+V9ovZdWoNz4yRXxljeN0H1bAhUwi5FyB+b6cKoydBDOAGmKc8rxBI+EXzM=";
    //生成密钥对
    public static void genKeyPair(){
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化密钥生成器 96-1024
        keyPairGen.initialize(1024,new SecureRandom());
        KeyPair keyPair = keyPairGen.genKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        String pulicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyStrig = new String(Base64.encodeBase64(privateKey.getEncoded()));
        keyMap.put(0,pulicKeyString);
        keyMap.put(1,privateKeyStrig);
    }
    //公钥加密，返回密文，每次返回的结果不一样
    public static String encrypt (String str, String publicKey) {
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = null;
        String outStr = null;
        try {
            pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return outStr;
    }

    /*
     * @Description
     * @Author CHENZEJIA
     * @Date 2019/12/24
     * @Param [str, privateKey] 加密字符串  私钥
     * @return java.lang.String 明文
     **/
    public static String decrypt(String str,String privateKey) {
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        //basa64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = null;
        Cipher cipher = null;
        String outStr = null;
        try {
            priKey = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return outStr;
    }
    public static void main(String [] args) {
        System.out.println("RSA加密");
        String message = "123";
        genKeyPair();
        System.out.println("随机生成的公钥为:" + RSAUtil.keyMap.get(0));
        System.out.println("随机生成的私钥为:" + RSAUtil.keyMap.get(1));
        String messageEn = encrypt(message,RSAUtil.PUBLIC_KEY);
        System.out.println("加密后的字符串为：" + messageEn);
        System.out.println("加密后的字符串为：" + messageEn.length());
        String mseeageDe = decrypt(messageEn,RSAUtil.PRIVATE_KEY);
        System.out.println("还原后的字符串为：" + mseeageDe);
    }
}
