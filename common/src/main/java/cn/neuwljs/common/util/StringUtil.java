package cn.neuwljs.common.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

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

    /**
     * 部分区域可点击的字符串的点击事件
     * @param text 文本
     * @param startPosition 开始位置
     * @param endPosition 结束为位置
     * @param clickable 点击事件
     */
    public static void clickOnSomeAreas(String text, int startPosition, int endPosition, Clickable clickable){
        SpannableString spannableString = new SpannableString (text);
        spannableString.setSpan (clickable, startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static class Clickable extends ClickableSpan{

        private View.OnClickListener mOnClickListener;

        public Clickable(View.OnClickListener listener){
            mOnClickListener = listener;
        }

        @Override
        public void onClick(@NonNull View widget) {
            mOnClickListener.onClick (widget);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState (ds);

            //去掉下划线,改变颜色
            ds.setUnderlineText (false);
            ds.setColor (0x4B97F3);
        }
    }
}
