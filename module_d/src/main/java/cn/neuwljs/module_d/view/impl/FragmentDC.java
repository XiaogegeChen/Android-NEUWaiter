package cn.neuwljs.module_d.view.impl;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.module_d.R;
import cn.neuwljs.widget.layout.Widget_ChooseLayout;
import cn.neuwljs.widget.layout.Widget_MessageLayout;

public class FragmentDC extends BaseFragment {

    private Widget_MessageLayout mNameLayout;
    private Widget_MessageLayout mIdLayout;
    private Widget_MessageLayout mCollegeLayout;
    private Widget_MessageLayout mPhoneLayout;
    private TextView mHintText;
    private Widget_ChooseLayout mLostType;
    private Widget_ChooseLayout mLostTime;
    private Widget_ChooseLayout mLostPlace;
    private EditText mLostInformation;
    private Button mCommitButton;

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_c;
    }

    @Override
    public void initView(View view) {
        mNameLayout = view.findViewById (R.id.module_d_fragment_d_c_my_name);
        mIdLayout = view.findViewById (R.id.module_d_fragment_d_c_my_id);
        mCollegeLayout = view.findViewById (R.id.module_d_fragment_d_c_my_college);
        mPhoneLayout = view.findViewById (R.id.module_d_fragment_d_c_my_phone);
        mHintText = view.findViewById (R.id.module_d_fragment_d_c_hint);
        mLostType = view.findViewById (R.id.module_d_fragment_d_c_my_lost_type);
        mLostTime = view.findViewById (R.id.module_d_fragment_d_c_my_lost_time);
        mLostPlace = view.findViewById (R.id.module_d_fragment_d_c_my_lost_place);
        mLostInformation = view.findViewById (R.id.module_d_fragment_d_c_my_lost_info);
        mCommitButton = view.findViewById (R.id.module_d_fragment_d_c_my_lost_commit);
    }

    @Override
    public void initData() {

    }
}
