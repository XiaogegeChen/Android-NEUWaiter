package cn.neuwljs.module_d.view;

import java.util.List;

import cn.neuwljs.common.base.IBaseView;
import cn.neuwljs.module_d.model.gson.Found;

public interface IFragmentDAAView extends IBaseView {
    /**
     * 显示初始的recyclerView
     * @param foundList 数据源
     */
    void showRecyclerView(List<Found> foundList);

    /**
     * 全部加载完成
     */
    void showDone();
}
