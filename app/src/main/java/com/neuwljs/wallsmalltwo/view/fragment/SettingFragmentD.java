package com.neuwljs.wallsmalltwo.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.helper.PhotoHelper;
import com.neuwljs.wallsmalltwo.view.widget.custom.layout.SettingBarLayout;
import com.neuwljs.wallsmalltwo.view.widget.custom.layout.TitleLayout;
import com.neuwljs.wallsmalltwo.view.widget.dialog.TakePhotoDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neuwljs.wallsmalltwo.util.helper.PhotoHelper.PHOTO_DIALOG_TAG;

/**
 * 失物招领的设置界面
 */
public class SettingFragmentD
        extends BaseFragment
        implements TitleLayout.OnArrowClickListener, View.OnClickListener {

    private static final String TAG = "SettingFragmentD";

    private TitleLayout mTitleLayout;
    private CircleImageView mHeadImage;
    private SettingBarLayout mImageHeadLayout;
    private SettingBarLayout mNameLayout;
    private SettingBarLayout mCollegeLayout;
    private SettingBarLayout mPhoneLayout;
    private SettingBarLayout mPasswordLayout;
    private Button mQuitButton;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting_d;
    }

    @Override
    public void initView(View view) {
        mTitleLayout = view.findViewById (R.id.fragment_setting_d_title);
        mHeadImage = view.findViewById (R.id.fragment_setting_d_head_image);
        mImageHeadLayout = view.findViewById (R.id.fragment_setting_d_head);
        mNameLayout = view.findViewById (R.id.fragment_setting_d_name);
        mCollegeLayout = view.findViewById (R.id.fragment_setting_d_college);
        mPhoneLayout = view.findViewById (R.id.fragment_setting_d_phone_number);
        mPasswordLayout = view.findViewById (R.id.fragment_setting_d_password);
        mQuitButton = view.findViewById (R.id.fragment_setting_d_quit);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        assert getFragmentManager () != null;
        TakePhotoDialog fragment = (TakePhotoDialog) getFragmentManager ().findFragmentByTag (PHOTO_DIALOG_TAG);
        assert fragment != null;
        fragment.onActivityResult (requestCode, resultCode, data);
    }

    /**
     * {@link com.neuwljs.wallsmalltwo.view.widget.custom.layout.TitleLayout.OnArrowClickListener}
     */
    @Override
    public void onLeftClick() {
        // 退出当前的activity
        obtainActivity ().finish ();
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
        switch (v.getId ()){
            case R.id.fragment_setting_d_head:
                PhotoHelper.getPhoto (getFragmentManager (), new PhotoHelper.Callback () {
                    @Override
                    public void onCancel() {
                        LogUtil.d (TAG, "onCancel");
                    }

                    @Override
                    public void onTakePhotoSuccess(Bitmap bitmap) {
                        LogUtil.d (TAG, "onTakePhotoSuccess");
                        mHeadImage.setImageBitmap (bitmap);
                    }

                    @Override
                    public void onTakePhotoFailure() {
                        LogUtil.d (TAG, "onTakePhotoFailure");
                    }

                    @Override
                    public void onChoosePhotoSuccess(Bitmap bitmap) {
                        LogUtil.d (TAG, "onChoosePhotoSuccess");
                        mHeadImage.setImageBitmap (bitmap);
                    }

                    @Override
                    public void onChoosePhotoFailure() {
                        LogUtil.d (TAG, "onChoosePhotoFailure");
                    }
                });
                break;
            default:
                break;
        }
    }
}
