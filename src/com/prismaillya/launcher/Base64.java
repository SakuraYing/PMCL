/*
 * 版权所有(C)Niconico Craft 保留所有权利
 * 您不得在未经作者许可的情况下，擅自发布本软件的任何部分或全部内容
 * 否则将会追究二次发布者的法律责任
 */
package com.prismaillya.launcher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {

    public static byte[] decode(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String enocode(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
