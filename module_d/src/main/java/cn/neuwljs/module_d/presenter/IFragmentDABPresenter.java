package cn.neuwljs.module_d.presenter;

import cn.neuwljs.common.base.IBasePresenter;
import cn.neuwljs.module_d.view.IFragmentDABView;

public interface IFragmentDABPresenter extends IBasePresenter<IFragmentDABView> {
    /**
     * 请求一页数据
     * @param page 页码，第几页
     */
    void queryPage(String page);

    /**
     * 请求下一页
     */
    void queryNext();

    /**
     * 刷新，从第一页开始重新加载
     */
    void refresh();
}
