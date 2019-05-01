package cn.neuwljs.module_d.presenter;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.view.IFragmentDBDView;

public interface IFragmentDBDPresenter extends IBasePresenter<IFragmentDBDView> {
    /**
     * 通知首页更新
     */
    void notifyFragmentDAARefresh();

    /**
     * 通知填写信息的第一页更新
     */
    void notifyFragmentDBRefresh();
}
