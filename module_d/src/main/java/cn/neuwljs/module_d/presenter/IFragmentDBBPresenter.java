package cn.neuwljs.module_d.presenter;

import android.graphics.Bitmap;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.view.IFragmentDBBView;

public interface IFragmentDBBPresenter extends IBasePresenter<IFragmentDBBView> {
    /**
     * 通知第三页的Property对象更新  详细信息
     * @param information 详细信息
     */
    void notifyPropertyRefresh(String information);

    /**
     * 通知第三页开始请求网络识别图片
     */
    void notifyFragmentDBCLoad();

    /**
     * 通知第三页刷新UI
     */
    void notifyFragmentDBCRefreshUI(String information);

    /**
     * 将图片保存并显示在ui上
     * @param bitmap 图片
     */
    void saveAndShow(Bitmap bitmap);
}
