package cn.neuwljs.login;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.Map;

import static cn.neuwljs.login.Constants.MAP;

/**
 * login模块的门面类,实现功能
 * 1.检查登陆状态并拿到已经登陆的用户的信息
 * 2.如果没有登陆直接跳转到登陆逻辑并执行登陆结果的回调
 * 3.注销登陆
 */
public class Login {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    /**
     * login初始化,在module中的IApp实现类中调用
     * 将这个map放进到主工程的map中，以保证放在
     * {@link cn.neuwljs.network.Network#init(Map)}中
     */
    public static Map<String, String> init(Context context){
        sContext = context;
        return MAP;
    }

    /**
     * 检查登陆状态并拿到已经登陆的用户的信息，这个方法不跳转到登陆界面进行登陆操作
     * @return 如果已经登陆，返回这个用户信息的bean,否则返回null
     */
    public static User checkLogin(){
        return LoginHelper.getInstance (sContext).checkLogin ();
    }

    /**
     * 检查登陆状态并拿到已经登陆的用户的信息，这个方法跳转到登陆界面进行登陆操作，并回调
     * @param callback 登录操作过后回到当前页面的回调
     */
    public static void checkLogin(Callback callback){
        LoginHelper.getInstance (sContext).checkLogin (callback);
    }

    /**
     * 注销登陆
     */
    public static void logout(){
        LoginHelper.getInstance (sContext).logout ();
    }
}
