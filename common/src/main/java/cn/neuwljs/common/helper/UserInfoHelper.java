package cn.neuwljs.common.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.neuwljs.common.base.model.User;
import cn.neuwljs.common.util.XmlIOUtil;

import static cn.neuwljs.common.base.Constants.XML_KEY_USER_INFORMATION;

/**
 * 拿到当前登陆用户信息的帮助类
 */
public class UserInfoHelper {

    private static boolean sLogin = false;

    /**
     * 拿到当前的登陆状态
     * @return 当前的登陆状态
     */
    public static boolean isLogin(){
        return sLogin;
    }

    /**
     * 设置为登陆状态
     */
    public static void login(){
        sLogin = true;
    }

    /**
     * 设置为未登录状态
     */
    public static void logout(){
        sLogin = false;
    }

    /**
     * 拿到当前已经登陆的用户的信息
     * @return 如果已经登陆，返回该用户的信息bean，否则返回null
     */
    public static User getUserInfo(Context context){
        if (!sLogin) {
            return null;
        }

        String userJson = XmlIOUtil.xmlGetString (XML_KEY_USER_INFORMATION, context);
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
}
