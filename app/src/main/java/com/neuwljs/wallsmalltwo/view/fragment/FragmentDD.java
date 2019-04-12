package com.neuwljs.wallsmalltwo.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.view.widget.custom.layout.ListLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDD extends BaseFragment {

    private CircleImageView mUserHeadImage;
    private TextView mUserName;
    private ImageView mSettingImage;
    private ListLayout mPublishLayout;
    private ListLayout mLostLayout;
    private ListLayout mDraftsLayout;
    private ListLayout mShareLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d_d;
    }

    @Override
    public void initView(View view) {
        mUserHeadImage = view.findViewById (R.id.fragment_d_d_user_head_image);
        mUserName = view.findViewById (R.id.fragment_d_d_user_name);
        mSettingImage = view.findViewById (R.id.fragment_d_d_set);
        mPublishLayout = view.findViewById (R.id.fragment_d_d_publish);
        mLostLayout = view.findViewById (R.id.fragment_d_d_lost);
        mShareLayout = view.findViewById (R.id.fragment_d_d_share);
        mDraftsLayout = view.findViewById (R.id.fragment_d_d_drafts);
    }

    @Override
    public void initData() {

    }
}
