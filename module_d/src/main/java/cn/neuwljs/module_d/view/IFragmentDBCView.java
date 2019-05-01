package cn.neuwljs.module_d.view;

import cn.neuwljs.common.base.IBaseView;
import cn.neuwljs.module_d.model.base.Owner;
import cn.neuwljs.module_d.model.base.Publisher;

public interface IFragmentDBCView extends IBaseView {
    /**
     * 显示识别后得到的失主信息
     * @param owner 识别后拿到的bean
     */
    void showOwner(Owner owner);

    /**
     * 显示发布者的信息
     * @param publisher 访问数据库拿到的Publisher实例
     */
    void showPublisher(Publisher publisher);

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
     * 跳转到最后一页
     */
    void goToNextPage();
}
