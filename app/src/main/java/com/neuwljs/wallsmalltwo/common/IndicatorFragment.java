package com.neuwljs.wallsmalltwo.common;

import com.neuwljs.wallsmalltwo.view.fragment.FragmentDB;

/**
 * 带指示器的view pager+fragment的fragment基类
 */
public abstract class IndicatorFragment extends BaseFragment {
    /**
     * 提供一个左右箭头被点击的监听器
     * @return 监听器
     */
    public abstract FragmentDB.OnArrowClickListener getOnArrowClickListener();
}
