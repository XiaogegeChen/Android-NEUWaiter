package cn.neuwljs.module_d.presenter.impl;

import cn.neuwljs.login.Callback;
import cn.neuwljs.login.Login;
import cn.neuwljs.login.User;
import cn.neuwljs.module_d.model.base.Publisher;
import cn.neuwljs.module_d.model.submit.Property;
import cn.neuwljs.module_d.presenter.IFragmentDCPresenter;
import cn.neuwljs.module_d.view.IFragmentDCView;

import static cn.neuwljs.module_d.Constants.LOGIN_FAILED;

/**
 * FragmentDC业务逻辑
 */
public class FragmentDCPresenterImpl implements IFragmentDCPresenter, Callback {

    private IFragmentDCView mFragmentDCView;

    @Override
    public void gotoLogin() {
        mFragmentDCView.dismissApply ();
        Login.checkLogin (this);
    }

    @Override
    public void cancelLogin() {
        mFragmentDCView.dismissApply ();
        mFragmentDCView.showToast (LOGIN_FAILED);
    }

    @Override
    public void upload(Property property) {

    }

    @Override
    public void uploadDirectly() {

    }

    @Override
    public void retry() {

    }

    @Override
    public void cancelAndSave(Property property) {

    }

    @Override
    public void loadPublisherAndShow() {

        // 拿到已经登陆user信息
        User user = Login.checkLogin ();

        if(user == null){

            // 未登录,弹出dialog
            mFragmentDCView.showApply ();
        }

        mFragmentDCView.showUserInfo (user);
    }

    @Override
    public void attach(IFragmentDCView fragmentDCView) {
        mFragmentDCView = fragmentDCView;
    }

    @Override
    public void detach() {
        mFragmentDCView = null;
    }

    /**
     * {@link Callback}
     */
    @Override
    public void onSuccess(User user) {
        loadPublisherAndShow ();
        notifyRefreshLoginStatus();
    }

    @Override
    public void onFailure() {
        mFragmentDCView.showToast (LOGIN_FAILED);
    }

    /**
     * User转化为Publisher
     */
    private Publisher user2Publisher(User user){

        if (user == null) {
            return null;
        }

        return new Publisher ();
    }

    /**
     * 通过发送事件通知所有包含用户信息的界面进行更新
     */
    private void notifyRefreshLoginStatus(){
        // TODO
    }

}
