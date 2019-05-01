package cn.neuwljs.module_d.view;

import java.util.List;

import cn.neuwljs.common.base.IBaseView;
import cn.neuwljs.module_d.model.gson.Lost;

public interface IFragmentDABView extends IBaseView {
    /**
     * 显示初始的recyclerView
     * @param lostList 数据源
     */
    void showRecyclerView(List<Lost> lostList);

    /**
     * 全部加载完成
     */
    void showDone();
}
