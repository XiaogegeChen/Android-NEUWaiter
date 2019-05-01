package cn.neuwljs.module_c;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.neuwljs.common.base.BaseFragment;

import static cn.neuwljs.common.route.RouterMap.MODULE_C_FRAGMENT_C_PATH;

@Route (path = MODULE_C_FRAGMENT_C_PATH)
public class FragmentC extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_c_fragment_c;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
}
