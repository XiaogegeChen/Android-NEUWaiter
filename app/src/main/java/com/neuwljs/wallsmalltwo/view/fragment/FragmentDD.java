package com.neuwljs.wallsmalltwo.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.util.ActivityUtil;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.ToastUtil;
import com.neuwljs.wallsmalltwo.util.helper.LoginHelper;
import com.neuwljs.wallsmalltwo.view.activity.SettingActivity;
import com.neuwljs.wallsmalltwo.view.widget.custom.layout.ListLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neuwljs.wallsmalltwo.model.Constant.IntentConstants.INTENT_PARAM_FROM_FRAGMENTDD_TO_SETTINGACTIVITY;

public class FragmentDD
        extends BaseFragment
        implements View.OnClickListener{

    private static final String TAG = "FragmentDD";

    private CircleImageView mUserHeadImage;
    private TextView mUserName;
    private ImageView mSettingImage;
    private ListLayout mPublishLayout;
    private ListLayout mLostLayout;
    private ListLayout mMyCard;
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
        mMyCard = view.findViewById (R.id.fragment_d_d_my_card);
        mShareLayout = view.findViewById (R.id.fragment_d_d_share);
        mDraftsLayout = view.findViewById (R.id.fragment_d_d_drafts);
    }

    @Override
    public void initData() {

        // 设置监听器
        mUserHeadImage.setOnClickListener (this);
        mPublishLayout.setOnClickListener (this);
        mSettingImage.setOnClickListener (this);
        mLostLayout.setOnClickListener (this);
        mMyCard.setOnClickListener (this);
        mDraftsLayout.setOnClickListener (this);
        mShareLayout.setOnClickListener (this);
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fragment_d_d_publish:
                LoginHelper.checkLogin (obtainContext (), new MyCallback (){
                    @Override
                    public void onCancel() {
                        ToastUtil.showToast (obtainContext (), "登陆失败");
                    }
                });
                break;

            case R.id.fragment_d_d_set:

                // 跳转到SettingActivity，并传递值用以通知SettingActivity加载相应的fragment
                ActivityUtil.startActivity (obtainContext (),
                        SettingActivity.class, INTENT_PARAM_FROM_FRAGMENTDD_TO_SETTINGACTIVITY);

                break;
            default:
                break;
        }
    }

    private static class MyCallback implements LoginHelper.Callback {

        @Override
        public void onLogin() {
            LogUtil.d (TAG, "onLogin: ");
        }

        @Override
        public void onCancel() {
        }
    }
}
