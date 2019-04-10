package com.neuwljs.wallsmalltwo.util;

import android.text.format.DateFormat;

public class StringUtil {
    /**
     * 判断字符串是不是null或者是不是""或者"      ";
     * @param s 字符串
     * @return 如果是null或者""或者"      "返回true,否则返回false
     */
    public static boolean isEmpty(String s){
        if(s == null){
            return true;
        }
        if(s.length () == 0){
            return true;
        }
        for(int i=0;i<s.length ();i++){
            if(s.charAt (i) != 32){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为纯数字
     * @param s 字符串
     * @return 如果是纯数字,返回true,否则返回false
     */
    public static boolean isAllNumber(String s){
        if(isEmpty (s)){
            return false;
        }else{
            for(int i=0; i<s.length (); i++){
                if((int) s.charAt (i) < 48 || (int) s.charAt (i) > 57){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 将一个用字符表示的时间转换成特定格式的时间
     * @param s 字符串
     * @return 特定格式的时间，格式为“2019年04月03日11:03”
     */
    public static String foramtTime(String s){
        long currentTime = 0;
        if(isAllNumber (s)){
            currentTime = Long.parseLong (s);
        }
        return (String) DateFormat.format ("yyyy年MM月dd日HH:mm", currentTime);
    }
}
