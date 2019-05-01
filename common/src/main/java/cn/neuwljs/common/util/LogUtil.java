package cn.neuwljs.common.util;

import android.util.Log;

import cn.neuwljs.common.AppConfig;

public class LogUtil {

    public static void d(String tag,String message){
        if(AppConfig.LOG){
            Log.d (tag,message);
        }
    }

    public static void e(String tag,String message){
        if(AppConfig.LOG){
            Log.e (tag,message);
        }
    }
}
