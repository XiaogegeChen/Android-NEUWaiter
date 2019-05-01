package cn.neuwljs.login;

/**
 * 该模块内部使用的callback
 */
interface LoginCallback {

    // 登陆成功
    void onSuccess(User user);

    // 登陆失败
    void onFailure();

}
