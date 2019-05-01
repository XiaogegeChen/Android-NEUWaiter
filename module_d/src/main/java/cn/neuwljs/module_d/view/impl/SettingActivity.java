package cn.neuwljs.module_d.view.impl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.neuwljs.common.base.BaseActivity;
import cn.neuwljs.module_d.R;
import cn.neuwljs.photo.PhotoHelper;
import cn.neuwljs.widget.layout.Widget_SettingBarLayout;
import cn.neuwljs.widget.layout.Widget_TitleLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 失物招领的设置界面
 */
public class SettingActivity
        extends BaseActivity
        implements Widget_TitleLayout.OnArrowClickListener, View.OnClickListener, PhotoHelper.Callback {

    private Widget_TitleLayout mTitleLayout;
    private CircleImageView mHeadImage;
    private Widget_SettingBarLayout mImageHeadLayout;
    private Widget_SettingBarLayout mNameLayout;
    private Widget_SettingBarLayout mCollegeLayout;
    private Widget_SettingBarLayout mPhoneLayout;
    private Widget_SettingBarLayout mPasswordLayout;
    private Button mQuitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public void initView() {
        mTitleLayout = findViewById (R.id.module_d_fragment_setting_title);
        mHeadImage = findViewById (R.id.module_d_fragment_setting_head_image);
        mImageHeadLayout = findViewById (R.id.module_d_fragment_setting_head);
        mNameLayout = findViewById (R.id.module_d_fragment_setting_name);
        mCollegeLayout = findViewById (R.id.module_d_fragment_setting_college);
        mPhoneLayout = findViewById (R.id.module_d_fragment_setting_phone_number);
        mPasswordLayout = findViewById (R.id.module_d_fragment_setting_password);
        mQuitButton = findViewById (R.id.module_d_fragment_setting_quit);
    }

    @Override
    public void initData() {
        mTitleLayout.setOnArrowClickListener (this);
        mImageHeadLayout.setOnClickListener (this);
        mNameLayout.setOnClickListener (this);
        mCollegeLayout.setOnClickListener (this);
        mPhoneLayout.setOnClickListener (this);
        mPasswordLayout.setOnClickListener (this);
        mQuitButton.setOnClickListener (this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_setting;
    }

    @Override
    public int getStatusBarColor() {
        return getContext ().getResources ().getColor (R.color.module_d_accent_color);
    }

    @Override
    public boolean isSupportSwipeBack() {

        // 支持滑动返回
        return true;
    }

    /**
     * {@link cn.neuwljs.widget.layout.Widget_TitleLayout.OnArrowClickListener}
     */
    @Override
    public void onLeftClick() {
        // 退出当前的activity
        finish ();
    }

    @Override
    public void onRightClick() {
        // 不处理，这个界面下不可见
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();

        if(id == R.id.module_d_fragment_setting_head){
            PhotoHelper.getPhoto (getSupportFragmentManager (), this);
        }
    }

    /**
     * {@link cn.neuwljs.photo.PhotoHelper.Callback}
     */
    @Override
    public void onCancel() {
    }

    @Override
    public void onTakePhotoSuccess(Bitmap bitmap) {
        mHeadImage.setImageBitmap (bitmap);
    }

    @Override
    public void onTakePhotoFailure() {
    }

    @Override
    public void onChoosePhotoSuccess(Bitmap bitmap) {
        mHeadImage.setImageBitmap (bitmap);
    }

    @Override
    public void onChoosePhotoFailure() {
    }
}
