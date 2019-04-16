package com.neuwljs.wallsmalltwo.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.IndicatorFragment;
import com.neuwljs.wallsmalltwo.presenter.impl.FragmentDBBPresenterImpl;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.helper.PhotoHelper;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.widget.NoSlideViewPager;
import com.neuwljs.wallsmalltwo.view.widget.dialog.TakePhotoDialog;

import static com.neuwljs.wallsmalltwo.model.Constant.CHOOSE_PHOTO_FAILED;
import static com.neuwljs.wallsmalltwo.model.Constant.TAKE_PHOTO_FAILED;
import static com.neuwljs.wallsmalltwo.util.helper.PhotoHelper.PHOTO_DIALOG_TAG;

public class FragmentDBB
        extends IndicatorFragment
        implements ViewContract.FragmentDBBView, View.OnClickListener, PhotoHelper.Callback {

    private static final String TAG = "FragmentDBB";

    private FrameLayout mTakePhotoFrameLayout;
    private ImageView mImageView;
    private EditText mEditText;


    // 业务逻辑实现的引用
    private FragmentDBBPresenterImpl mFragmentDBBPresenter;

    //当前的ViewPager实例
    private NoSlideViewPager mNoSlideViewPager;

    public void setNoSlideViewPager(NoSlideViewPager noSlideViewPager) {
        mNoSlideViewPager = noSlideViewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDBBPresenter.attach (this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDBBPresenter.detach ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d_b_b;
    }

    @Override
    public void initView(View view) {
        mTakePhotoFrameLayout = view.findViewById (R.id.fragment_d_b_b_take_photo);
        mImageView = view.findViewById (R.id.fragment_d_b_b_image);
        mEditText = view.findViewById (R.id.fragment_d_b_b_edit_text);
    }

    @Override
    public void initData() {
        mFragmentDBBPresenter = new FragmentDBBPresenterImpl (this);
        mTakePhotoFrameLayout.setOnClickListener (this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        assert getFragmentManager () != null;
        TakePhotoDialog fragment = (TakePhotoDialog) getFragmentManager ().findFragmentByTag (PHOTO_DIALOG_TAG);
        assert fragment != null;
        fragment.onActivityResult (requestCode, resultCode, data);
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fragment_d_b_b_take_photo:
                PhotoHelper.getPhoto (getFragmentManager (), this);
                break;
            default:
                break;

        }
    }

    /**
     * {@link ViewContract.FragmentDBBView}
     */
    @Override
    public void showProgressPage() {
    }

    @Override
    public void showErrorPage() {
    }

    @Override
    public void showToast(String message) {
    }

    @Override
    public void showImage(Bitmap bitmap) {
        if(mImageView.getVisibility () != View.VISIBLE){
            mImageView.setVisibility (View.VISIBLE);
        }
        mImageView.setImageBitmap (bitmap);
    }

    @Override
    public FragmentDB.OnArrowClickListener getOnArrowClickListener() {
        return new FragmentDB.OnArrowClickListener () {
            @Override
            public void onLeftClick() {
                //跳转
                mNoSlideViewPager.setCurrentItem (0, true);
            }

            @Override
            public void onRightClick() {
                LogUtil.d (TAG, "FragmentDBB: onRightClick");

                //通知更改信息
                String information = mEditText.getText ().toString ();
                mFragmentDBBPresenter.notifyPropertyRefresh (information);

                //通知加载
                mFragmentDBBPresenter.notifyFragmentDBCLoad ();
                mFragmentDBBPresenter.notifyFragmentDBCRefreshUI (information);

                //跳转
                mNoSlideViewPager.setCurrentItem (2, true);
            }
        };
    }

    @Override
    public void refresh() {
        // 图片不可见,输入框清空
        if(mImageView.getVisibility () != View.INVISIBLE){
            mImageView.setVisibility (View.INVISIBLE);
        }
        mEditText.setText (null);
    }

    /**
     * {@link com.neuwljs.wallsmalltwo.util.helper.PhotoHelper.Callback}
     */
    @Override
    public void onCancel() {
    }

    @Override
    public void onTakePhotoSuccess(Bitmap bitmap) {
        mFragmentDBBPresenter.saveAndShow (bitmap);
    }

    @Override
    public void onTakePhotoFailure() {
        showToast (TAKE_PHOTO_FAILED);
    }

    @Override
    public void onChoosePhotoSuccess(Bitmap bitmap) {
        mFragmentDBBPresenter.saveAndShow (bitmap);
    }

    @Override
    public void onChoosePhotoFailure() {
        showToast (CHOOSE_PHOTO_FAILED);
    }
}
