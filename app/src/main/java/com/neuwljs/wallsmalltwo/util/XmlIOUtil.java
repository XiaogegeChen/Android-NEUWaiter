package com.neuwljs.wallsmalltwo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.neuwljs.wallsmalltwo.model.Constant.XML_DEFAULT_VALUE_LONG;
import static com.neuwljs.wallsmalltwo.model.Constant.XML_DEFAULT_VALUE_STRING;

public class XmlIOUtil {

    private XmlIOUtil(){
    }

    public static void xmlPut(String key, String value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    public static void xmlPut(String key, long value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putLong (key, value);
        editor.apply ();
    }

    public static String xmlGetString(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getString (key, XML_DEFAULT_VALUE_STRING);
    }

    public static long xmlGetLong(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getLong (key, XML_DEFAULT_VALUE_LONG);
    }
}
