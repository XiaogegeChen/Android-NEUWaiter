package cn.neuwljs.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static cn.neuwljs.login.Constants.LOGIN_XML_KEY_USER_INFORMATION;
import static cn.neuwljs.login.Constants.sLoginCallback;

/**
 * 登陆操作帮助类，单例
 */
class LoginHelper {

    @SuppressLint("StaticFieldLeak")
    private static LoginHelper sSingleton;

    private Context mContext;

    private LoginHelper(Context context){
        mContext = context;
    }

    static LoginHelper getInstance(Context context){
        if(sSingleton == null){
            synchronized (LoginHelper.class){
                if (sSingleton == null) {
                    sSingleton = new LoginHelper (context);
                }
            }
        }

        return sSingleton;
    }

    /**
     * 检查用户是否已经登陆,如果已经登陆，返回改用户的信息bean,否则返回null
     */
    User checkLogin(){
        String userJson = XmlIOUtil.xmlGetString (LOGIN_XML_KEY_USER_INFORMATION, mContext);
        if (userJson == null) {
            return null;
        }

        User user;
        try {
            user = new Gson ().fromJson (userJson, User.class);
        } catch (JsonSyntaxException e){
            e.printStackTrace ();
            return null;
        }

        return user;
    }

    void checkLogin(Callback callback){
        User user = checkLogin ();

        if (user != null) {
            callback.onSuccess (user);
            return;
        }

        // 给回调sLoginCallback赋值
        sLoginCallback = new LoginCallback () {
            @Override
            public void onSuccess(User user) {

                // 成功,回调onSuccess并置空sLoginCallback
                callback.onSuccess (user);
                sLoginCallback = null;
            }

            @Override
            public void onFailure() {

                // 登陆不成功，可能是失败或者用户取消调用,回调onFailure并置空sLoginCallback
                callback.onFailure ();
                sLoginCallback = null;
            }
        };

        // 跳转到登陆界面登陆
        Intent intent = new Intent (mContext, LoginActivity.class);
        intent.setFlags (FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity (intent);
    }

    void logout(){

        // 把信息清空
        XmlIOUtil.xmlPut (LOGIN_XML_KEY_USER_INFORMATION, null, mContext);
    }

}
