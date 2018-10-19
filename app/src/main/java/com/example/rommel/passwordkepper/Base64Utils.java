/**
 * form https://github.com/songxiaoliang/EncryptionLib/blob/master/app/src/main/java/com/android/song/encryptionlib/AESUtils.java
 */
package com.example.rommel.passwordkepper;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Base64编解码工具类
 * Base64.DEFAULT: 使用默认方式加密
 * Created by Song on 2017/2/22.
 */

public class Base64Utils {

    private Base64Utils() {
        throw new UnsupportedOperationException("constrontor cannot be init");
    }

    /**
     * 字符串编码
     * @param data 需要编码的数据
     * @return 编码后的数据
     */
    public static String encodedStr(byte[] data) {

        return Base64.encodeToString(data,Base64.DEFAULT);
    }

    /**
     * 字符串解码
     * @param data 需要解码的数据
     * @return 解码后的数据
     */
    public static byte[] decodedStr(String data) {

        return Base64.decode(data,Base64.DEFAULT);
    }
}