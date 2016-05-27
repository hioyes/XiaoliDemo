package com.xiaoli.library.utils;

import java.io.UnsupportedEncodingException;

/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName: StringUtils
 * @date 2014-11-6 下午8:17:55
 * @Description:常用工具类
 */
public class StringUtils {

    /**
     * 如果为null返回空字符串，适用于文本显示的处理
     *
     * @param str
     * @return
     */
    public static String toString(String str) {
        if (str == null) return "";
        return str;
    }

    /**
     * 验证字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串转成浮点，错误值返回0
     *
     * @param str
     * @return
     */
    public static double todouble(String str) {
        if (str == null || "".equals(str)) return 0;
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    /**
     * 字符串转成浮点，错误值返回Null
     *
     * @param str
     * @return
     */
    public static Double toDouble(String str) {
        if (str == null || "".equals(str)) return null;
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 字符串转成Integer,非数字返回Null
     *
     * @param str
     * @return
     */
    public static Integer toInteger(String str) {
        if (str == null || "".equals(str)) return null;
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 字符串转成Long,非数字返回Null
     *
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        if (str == null || "".equals(str)) return null;
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


    /**
     * @param str 参数string
     * @return 验证是否为空后的字符串
     */
    public static String getString(String str) {
        return !isNullOrEmpty(str) ? str : "";
    }

    /**
     * 判断 字符串首字符 是否为 字母
     *
     * @param fstrData
     * @return
     */
    public static boolean checkIsLetter(String fstrData) {
        if (isNullOrEmpty(fstrData)) return false;
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 改变 字符串的编码格式
     *
     * @param data
     * @param charset
     * @return
     */
    public static String changeCharset(final byte[] data, final String charset) {
        try {
            return new String(data, 0, data.length, charset);
        } catch (final UnsupportedEncodingException e) {
            return new String(data, 0, data.length);
        }
    }
}
