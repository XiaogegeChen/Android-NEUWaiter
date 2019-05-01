package cn.neuwljs.ocr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class XmlIOUtil {

    static void xmlPut(String key, String value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    static void xmlPut(String key, long value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putLong (key, value);
        editor.apply ();
    }

    static String xmlGetString(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getString (key, null);
    }

    static long xmlGetLong(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getLong (key, 0);
    }
}
