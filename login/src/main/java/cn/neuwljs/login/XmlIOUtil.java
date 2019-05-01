package cn.neuwljs.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class XmlIOUtil {

    static void xmlPut(String key, String value, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    static String xmlGetString(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (context);
        return preferences.getString (key, null);
    }

}
