package cn.neuwljs.module_a;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.neuwljs.common.base.BaseFragment;

import static cn.neuwljs.common.route.RouterMap.MODULE_A_FRAGMENT_A_PATH;

@Route (path = MODULE_A_FRAGMENT_A_PATH)
public class FragmentA extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_a_fragment_a;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
}
