package com.neuwljs.wallsmalltwo.util.helper;

import android.content.Context;

import com.neuwljs.wallsmalltwo.util.ActivityUtil;
import com.neuwljs.wallsmalltwo.view.activity.LoginActivityD;

/**
 * 检查用户登陆状态的帮助类
 */
public class LoginHelper {

    /**
     * 全局登陆凭证，如果是空的，代表未登录，不是空的代表已经登陆
     */
    public static String sUserMessage;

    /**
     * 全局登陆状态的回调，这个回调在登陆界面进行登陆操作后会回调
     * 使用前先赋值，使用后要清空。
     */
    public static LoginCallback sLoginCallback;

    /**
     * 先检查是否登陆，如果已经登陆，直接回调{@link Callback#onLogin()},
     * 如果未登陆,跳转登陆界面进行登陆,如果成功，回调{@link Callback#onLogin()}
     * 如果失败，回调{@link Callback#onCancel()}
     * @param callback 回调
     */
    public static void checkLogin(Context context, final Callback callback){
        if(isLogin ()){
            callback.onLogin ();
            return;
        }

        // 给回调sLoginCallback赋值
        sLoginCallback = new LoginCallback () {
            @Override
            public void onSuccess() {

                // 成功,回调onLogin并置空sLoginCallback
                callback.onLogin ();
                sLoginCallback = null;
            }

            @Override
            public void onFailure() {

                // 登陆不成功，可能是失败或者用户取消调用,回调onCanael并置空sLoginCallback
                callback.onCancel ();
                sLoginCallback = null;
            }
        };

        // 跳转登陆界面
        ActivityUtil.startActivity (context, LoginActivityD.class);
    }

    /**
     * 判断目前的登陆状态
     * @return 已经登陆返回true,否则返回false
     */
    private static boolean isLogin(){
        return sUserMessage != null;
    }

    /**
     * 整个判断过程的回调，包括已经登陆时的回调
     * 和调到登陆界面但是登陆失败的回调
     */
    public interface Callback{
        /**
         * 已经登陆或者登陆成功的回调
         */
        void onLogin();

        /**
         * 用户主动取消登陆，或者登陆失败的回调
         */
        void onCancel();
    }

    /**
     * 登陆界面登陆时的回调
     */
    public interface LoginCallback{
        /**
         * 在登陆界面如果登陆成功的回调
         */
        void onSuccess();

        /**
         * 在登陆界面如果登陆不成功的回调
         */
        void onFailure();
    }
}
