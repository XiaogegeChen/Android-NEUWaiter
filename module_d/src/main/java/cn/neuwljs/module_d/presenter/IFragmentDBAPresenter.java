package cn.neuwljs.module_d.presenter;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.view.IFragmentDBAView;

public interface IFragmentDBAPresenter extends IBasePresenter<IFragmentDBAView> {
    /**
     * 通知第三页的Property对象更新失物类型
     * @param lostPropertyType 失物类型
     */
    void notifyPropertyRefresh(String lostPropertyType);
}
