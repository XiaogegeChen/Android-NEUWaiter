package cn.neuwljs.login;

/**
 * 进行登陆后登陆状态的回调
 */
public interface Callback {

    /**
     * 登陆成功
     * @param user 该用户的信息
     */
    void onSuccess(User user);

    /**
     * 登陆失败或者登陆被取消
     */
    void onFailure();

}
