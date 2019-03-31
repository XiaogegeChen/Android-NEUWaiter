package com.neuwljs.wallsmalltwo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.neuwljs.wallsmalltwo.model.Constant.XML_DEFAULT_VALUE;

public class XmlIOUtil {

    private XmlIOUtil(){
    }

    public static void xmlPut(String key, String value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    public static String xmlGet(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getString (key, XML_DEFAULT_VALUE);
    }
}
