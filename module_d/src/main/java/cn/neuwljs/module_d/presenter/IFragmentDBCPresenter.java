package cn.neuwljs.module_d.presenter;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.model.base.Publisher;
import cn.neuwljs.module_d.model.submit.Property;
import cn.neuwljs.module_d.view.IFragmentDBCView;

public interface IFragmentDBCPresenter extends IBasePresenter<IFragmentDBCView> {
    /**
     * 请求网络拿到识别得到的结果并显示
     */
    void loadOwner();

    /**
     * 从本地拿到Publisher的实例并显示
     */
    Publisher loadPublisher();

    /**
     * 向服务器上传发布的这个信息
     * @param property 物品实例
     */
    void upload(Property property);

    /**
     * 重新上传信息
     */
    void retry();

    /**
     * 关闭对话框并保存之前填写的信息
     * @param property
     */
    void cancelAndSave(Property property);
}
