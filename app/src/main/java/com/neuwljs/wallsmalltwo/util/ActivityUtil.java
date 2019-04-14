package com.neuwljs.wallsmalltwo.util;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import static com.neuwljs.wallsmalltwo.model.Constant.IntentConstants.INTENT_PARAM_PARCELABLE_KEY;
import static com.neuwljs.wallsmalltwo.model.Constant.IntentConstants.INTENT_PARAM_STRING_KEY;

public class ActivityUtil {

    /**
     * 提供一个启动activity的方法
     * @param context 发起启动的上下文
     * @param cls 被启动的活动
     */
    public static void startActivity(Context context, Class<? extends AppCompatActivity> cls){
        Intent intent = new Intent (context, cls);
        context.startActivity (intent);
    }

    /**
     * 提供一个启动activity的方法
     * @param context 发起启动的上下文
     * @param cls 被启动的活动
     * @param p 传递的Parcelable
     */
    public static void startActivity(Context context, Class<? extends AppCompatActivity> cls, Parcelable p){
        Intent intent = new Intent (context, cls);
        intent.putExtra (INTENT_PARAM_PARCELABLE_KEY, p);
        context.startActivity (intent);
    }

    /**
     * 提供一个启动activity的方法
     * @param context 发起启动的上下文
     * @param cls 被启动的活动
     * @param s 传递的字符串，
     */
    public static void startActivity(Context context, Class<? extends AppCompatActivity> cls, String s){
        Intent intent = new Intent (context, cls);
        intent.putExtra (INTENT_PARAM_STRING_KEY, s);
        context.startActivity (intent);
    }

}
