package cn.neuwljs.module_d.presenter;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.view.IFragmentDDView;

public interface IFragmentDDPresenter extends IBasePresenter<IFragmentDDView> {

    /**
     * 跳转到UserActivity界面
     */
    void gotoUserActivity();

    /**
     * 跳转到UserSubmitActivity界面
     */
    void gotoUserSubmitActivity();
}
