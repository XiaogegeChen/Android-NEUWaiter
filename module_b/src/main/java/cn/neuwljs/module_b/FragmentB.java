package cn.neuwljs.module_b;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.neuwljs.common.base.BaseFragment;

import static cn.neuwljs.common.route.RouterMap.MODULE_B_FRAGMENT_B_PATH;

@Route (path = MODULE_B_FRAGMENT_B_PATH)
public class FragmentB extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_b_fragment_b;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
}
