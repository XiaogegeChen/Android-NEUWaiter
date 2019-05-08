package cn.neuwljs.module_d.view;

import cn.neuwljs.common.base.IBaseView;
import cn.neuwljs.login.User;

public interface IFragmentDCView extends IBaseView {

    /**
     * 显示请求登陆对话框
     */
    void showApply();

    /**
     * 关闭请求登陆对话框
     */
    void dismissApply();

    /**
     * 显示用户的基本信息
     * @param user 用户bean
     */
    void showUserInfo(User user);

    /**
     * 显示上传对话框
     */
    void showLoading();

    /**
     * 关闭上传对话框
     */
    void dismissLoading();

    /**
     * 显示上传失败对话框
     */
    void showFailure();

    /**
     * 关闭上传失败对话框
     */
    void dismissFailure();

    /**
     * 显示上传成功对话框
     */
    void showSuccess();

    /**
     * 关闭上传成功对话框
     */
    void dismissSuccess();
}
