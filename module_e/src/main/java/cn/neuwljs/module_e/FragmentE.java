package cn.neuwljs.module_e;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.neuwljs.common.base.BaseFragment;

import static cn.neuwljs.common.route.RouterMap.MODULE_E_FRAGMENT_E_PATH;

@Route (path = MODULE_E_FRAGMENT_E_PATH)
public class FragmentE extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_e_fragment_e;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
}
