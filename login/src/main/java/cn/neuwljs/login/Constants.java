package cn.neuwljs.login;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

    // 全局的登陆回调接口这个回调在登陆界面进行登陆操作后会回调，使用前先赋值，使用后要清空。
    static LoginCallback sLoginCallback;

    // 用户信息在xml中的key
    static final String LOGIN_XML_KEY_USER_INFORMATION = "login_xml_key_user_information";

    // 使用到的head的key和baseurl map
    static final Map<String, String> MAP;

    // 用于动态更改baseurl的head的key
    static final String OKHTTP_HEAD_NAME = "okhttp_head_name";

    static {
        MAP = new HashMap<> ();
    }

}
