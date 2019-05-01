package cn.neuwljs.network;

import android.util.Log;

class LogUtil {

    private static boolean sOpenLog = true;

    static void openLog(boolean b){
        sOpenLog = b;
    }

    static void d(String tag, String message){
        if (sOpenLog){
            Log.d (tag, message);
        }
    }
}
