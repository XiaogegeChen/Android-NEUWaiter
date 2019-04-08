package com.neuwljs.wallsmalltwo.util;

import android.view.View;
import android.widget.TextView;

/**
 * 提供一些处理view的常用方法
 */
public class ViewUtil {

    /**
     * 如果这个view的数据是空的,就把这个view设置为{@link android.view.View#GONE}
     */
    public static void handleView(TextView view, String text){
        if(StringUtil.isEmpty (text)){
            view.setVisibility (View.GONE);
        }else{
            view.setText (text);
        }
    }

}
