package com.neuwljs.wallsmalltwo.util;

public class StringUtil {
    /**
     * 判断字符串是不是null或者是不是"";
     * @param s 字符串
     * @return 如果是null或者""返回true,否则返回false
     */
    public static boolean isEmpty(String s){
        return s == null || s.isEmpty ();
    }
}
