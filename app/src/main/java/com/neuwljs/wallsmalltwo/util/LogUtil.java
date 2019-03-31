package com.neuwljs.wallsmalltwo.util;

import android.util.Log;

import com.neuwljs.wallsmalltwo.common.AppConfig;

public class LogUtil {

    public static void d(String tag,String message){
        if(AppConfig.LOG){
            Log.d (tag,message);
        }
    }
}
