package cn.neuwljs.module_d;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.module_d.view.impl.FragmentDB;

/**
 * 带指示器的view pager+fragment的fragment基类
 */
public abstract class IndicatorFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 注册eventBus
        EventBus.getDefault ().register (this);
        return super.onCreateView (inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();

        // 取消注册eventBus
        EventBus.getDefault ().unregister (this);
    }

    /**
     * 提供一个左右箭头被点击的监听器
     * @return 监听器
     */
    public abstract FragmentDB.OnArrowClickListener getOnArrowClickListener();

    public abstract void refresh();

    /**
     * 刷新事件的响应方法
     * @param event 刷新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event){
        if(event.isBegin ()){
            refresh ();
        }
    }

    /**
     * 通知碎片刷新的事件
     */
    public static class RefreshEvent{
        private boolean begin;

        public boolean isBegin() {
            return begin;
        }

        public void setBegin(boolean begin) {
            this.begin = begin;
        }
    }
}
