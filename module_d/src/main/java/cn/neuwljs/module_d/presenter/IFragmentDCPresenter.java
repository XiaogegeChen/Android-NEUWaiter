package cn.neuwljs.module_d.presenter;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.model.base.Publisher;
import cn.neuwljs.module_d.model.submit.Property;
import cn.neuwljs.module_d.view.IFragmentDCView;

public interface IFragmentDCPresenter extends IBasePresenter<IFragmentDCView> {

    /**
     * 去登陆
     */
    void gotoLogin();

    /**
     * 取消登陆
     */
    void cancelLogin();

    /**
     * 上传
     */
    void upload(Property property);

    /**
     * 一键上传
     */
    void uploadDirectly();

    /**
     * 重新上传信息
     */
    void retry();

    /**
     * 取消上传并保存
     */
    void cancelAndSave(Property property);

    /**
     * 从本地拿到Publisher的实例并显示
     */
    void loadPublisherAndShow();
}