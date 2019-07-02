package com.rainfly.musicapi.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 加解密工具
 */
@Slf4j
public class CryptoUtils {

    //私key
    private static final String NONCE = "0CoJUm6Qyw8W8jud";
    //公key
    private static final String pubKey = "010001";
    //
    private static final String modules = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
    //向量iv
    private static final String ivCode = "0102030405060708";

    /**
     * AES随机生成秘钥
     * @param size 长度
     * @return 返回生成的秘钥
     */
    private static String createSecretKey(int size){
        String keys = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder key = new StringBuilder();

        for(int i=0;i<size;i++){
            int pos = (int) Math.floor(Math.random() * keys.length());
            key.append(pos);
        }
        return key.toString();
    }

    /**
     * AES 加密
     * @param text 需要加密的文本
     * @param secKey 秘钥
     * @return
     */
    private static String aesEncrypt(String text, String secKey) throws Exception {
        byte[] ivCodeBytes = ivCode.getBytes();
        if(secKey==null){
            log.error("【aesEncrypt】Key为null");
            return null;
        }
        //判断key是否是16位
        if(secKey.length()!=16){
            log.error("【aesEncrypt】Key长度不是16位!");
            return null;
        }

        byte[] sKeyRaw = secKey.getBytes("utf-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(sKeyRaw,"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(ivCodeBytes);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,iv);
        byte[] encrypted  = cipher.doFinal(text.getBytes());
        return Base64.encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     *RSA 加密
     * @return
     */
    private static String rsaEncrypt(String secKey, String pubKey, String modules){
        // 倒序 key
        String rKey = StrUtil.reverse(secKey);
        // 将 key 转 ascii 编码 然后转成 16 进制字符串
        String hexRKey = HexUtil.encodeHexStr(rKey.getBytes());
        //将 16进制 的 三个参数 转为10进制的 bigint
        BigInteger bigRkey = new BigInteger( new BigInteger(hexRKey,16).toString(10));
        BigInteger bigPubKey = new BigInteger(new BigInteger(pubKey,16).toString(10)) ;
        BigInteger bigModules =new BigInteger(new BigInteger(modules,16).toString(10)) ;
        //执行幂乘取模运算得到最终的bigint结果
        bigRkey = bigRkey.modPow(bigPubKey,bigModules);
        //将结果转换为16进制字符串
        String bigRkeyStr = bigRkey.toString(16);
        // 可能存在不满256位的情况，要在前面补0补满256位
        return zFill(bigRkeyStr,256);
    }

    /**
     * 不满某个位数，前面补0
     * @param str 需要补0的字符串
     * @param size 位数
     * @return
     */
    private static String zFill(String str, int size){
        while (str.length()<size){
            str = "0"+str;
        }
        return str;
    }
    /**
     * 将字符串转换成ASCII嘛
     * @param str
     * @return
     */
    private static String convertToASCII(String str){
        StringBuilder builder = new StringBuilder();
        for(char ch : str.toCharArray()){
            builder.append(Integer.valueOf(ch).intValue());
        }
        return builder.toString();
    }

    /**
     * 加密
     * @param json 需要加密的参数
     * @return 返回加密后的数据 params:加密后的参数  encSecKey:加密后的秘钥
     * @throws Exception 异常
     */
    public Map<String,String> Encrypt(String json) throws Exception {
        String secKey = createSecretKey(16);
        String encText = aesEncrypt(aesEncrypt(json,NONCE),secKey);
        String encSecKey = rsaEncrypt(secKey,pubKey,modules);
        Map<String,String> result = new HashMap<>();
        result.put("params",encText);
        result.put("encSecKey",encSecKey);
        return result;
    }

}
