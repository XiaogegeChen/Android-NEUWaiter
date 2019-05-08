package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.neuwljs.common.dialog.UploadFailDialog;
import cn.neuwljs.common.dialog.UploadProgressDialog;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.common.util.StringUtil;
import cn.neuwljs.common.util.ToastUtil;
import cn.neuwljs.module_d.IndicatorFragment;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.model.base.LostOrFound;
import cn.neuwljs.module_d.model.base.Owner;
import cn.neuwljs.module_d.model.base.Publisher;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCDisplayInformationEvent;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCLoadOwnerAndPublisherEvent;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCRefreshPropertyInformationEvent;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCRefreshPropertyTypeEvent;
import cn.neuwljs.module_d.model.submit.Property;
import cn.neuwljs.module_d.presenter.impl.FragmentDBCPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDBCView;
import cn.neuwljs.widget.NoSlideViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.neuwljs.module_d.Constants.COLLEGE_MAX_LENGTH;
import static cn.neuwljs.module_d.Constants.ID_MAX_LENGTH;
import static cn.neuwljs.module_d.Constants.NAME_MAX_LENGTH;
import static cn.neuwljs.module_d.Constants.OWNER_COLLEGE_DEFAULT;
import static cn.neuwljs.module_d.Constants.OWNER_ID_DEFAULT;
import static cn.neuwljs.module_d.Constants.OWNER_NAME_DEFAULT;
import static cn.neuwljs.module_d.Constants.PUBLISHER_NAME_DEFAULT;

public class FragmentDBC
        extends IndicatorFragment
        implements View.OnClickListener, IFragmentDBCView,
        FragmentDB.OnArrowClickListener, UploadFailDialog.OnButtonClickListener {

    private static final String TAG = "FragmentDBC";
    private static final String DIALOG_LOAD_TAG = "dialog_load_tag";
    private static final String DIALOG_FAIL_TAG = "dialog_fail_tag";

    //控件
    private Button mSubmitButton;
    private FrameLayout mProgressPage;
    private LinearLayout mContentPage;
    private CircleImageView mPublisherHeadImage;
    private TextView mPublisherName;
    private TextView mPublishTime;
    private TextView mPublishInformation;
    private TextView mOwnerName;
    private TextView mOwnerId;
    private TextView mOwnerCollege;
    private EditText mOwnerNameEdit;
    private EditText mOwnerIdEdit;
    private EditText mOwnerCollegeEdit;

    // 进度对话框
    private UploadProgressDialog mProgressDialog;

    // 上传失败对话框
    private UploadFailDialog mUploadFailDialog;

    //当前的ViewPager实例
    private NoSlideViewPager mNoSlideViewPager;

    // 业务逻辑的实例
    private FragmentDBCPresenterImpl mFragmentDBCPresenter;

    /**
     * 用户填写的失物信息的汇总实例,提交时直接提交并把它置为空
     */
    private Property mProperty;

    public void setNoSlideViewPager(NoSlideViewPager noSlideViewPager) {
        mNoSlideViewPager = noSlideViewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDBCPresenter.attach (this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDBCPresenter.detach ();
    }

    public FragmentDBC() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_b_c;
    }

    @Override
    public void initView(View view) {
        mSubmitButton = view.findViewById (R.id.module_d_fragment_d_b_c_submit);
        mProgressPage = view.findViewById (R.id.module_d_fragment_d_b_c_progress_page);
        mContentPage = view.findViewById (R.id.module_d_fragment_d_b_c_content_page);
        mPublisherHeadImage = view.findViewById (R.id.module_d_fragment_d_b_c_publisher_head_image);
        mPublisherName = view.findViewById (R.id.module_d_fragment_d_b_c_publisher_name);
        mPublishTime = view.findViewById (R.id.module_d_fragment_d_b_c_publisher_time);
        mPublishInformation = view.findViewById (R.id.module_d_fragment_d_b_c_publisher_information);
        mOwnerName = view.findViewById (R.id.module_d_fragment_d_b_c_owner_name);
        mOwnerId = view.findViewById (R.id.module_d_fragment_d_b_c_owner_id);
        mOwnerCollege = view.findViewById (R.id.module_d_fragment_d_b_c_owner_college);
        mOwnerNameEdit = view.findViewById (R.id.module_d_fragment_d_b_c_owner_name_edit);
        mOwnerIdEdit = view.findViewById (R.id.module_d_fragment_d_b_c_owner_id_edit);
        mOwnerCollegeEdit = view.findViewById (R.id.module_d_fragment_d_b_c_owner_college_edit);
    }

    @Override
    public void initData() {
        mSubmitButton.setOnClickListener (this);
        mFragmentDBCPresenter = new FragmentDBCPresenterImpl (this);

        // editText监听器
        mOwnerNameEdit.addTextChangedListener (new MyTextWatcher () {
            @Override
            public void afterTextChanged(Editable s) {

                if(s.length () <= NAME_MAX_LENGTH){

                    // 更改mOwnerName
                    mOwnerName.setText (s);
                }
            }
        });

        mOwnerIdEdit.addTextChangedListener (new MyTextWatcher () {
            @Override
            public void afterTextChanged(Editable s) {
                if(StringUtil.isAllNumber (s.toString ()) && s.length () <= ID_MAX_LENGTH){

                    // 更改mOwnerId
                    mOwnerId.setText (s);
                }
            }
        });

        mOwnerCollegeEdit.addTextChangedListener (new MyTextWatcher () {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length () <= COLLEGE_MAX_LENGTH){

                    // 更改mOwnerCollege
                    mOwnerCollege.setText (s);
                }
            }
        });

        mProgressDialog = new UploadProgressDialog ();
        mUploadFailDialog = new UploadFailDialog ();
        mUploadFailDialog.setOnButtonClickListener (this);

        // 实例化mProperty
        mProperty = new Property ();
    }

    @Override
    public FragmentDB.OnArrowClickListener getOnArrowClickListener() {
        return this;
    }

    @Override
    public void refresh() {
        // 清空mProperty
        mProperty = new Property ();

        // 清空所有控件状态
        if(mProgressPage.getVisibility () != View.VISIBLE){
            mProgressPage.setVisibility (View.VISIBLE);
        }
        if(mContentPage.getVisibility () != View.INVISIBLE){
            mContentPage.setVisibility (View.INVISIBLE);
        }
        mPublisherHeadImage.setImageResource (R.drawable.module_d_head_image_default);
        mPublisherName.setText (null);
        mPublishTime.setText (null);
        mPublishInformation.setText (null);
        mOwnerName.setText(null);
        mOwnerId.setText (null);
        mOwnerCollege.setText (null);
        mOwnerNameEdit.setText (null);
        mOwnerIdEdit.setText (null);
        mOwnerCollegeEdit.setText (null);
    }

    @Override
    public void showProgressPage() {
        mContentPage.setVisibility (View.INVISIBLE);
        mProgressPage.setVisibility (View.VISIBLE);
    }

    @Override
    public void showErrorPage() {
        mProgressPage.setVisibility (View.INVISIBLE);
        mContentPage.setVisibility (View.VISIBLE);
        mOwnerName.setText (OWNER_NAME_DEFAULT);
        mOwnerId.setText (OWNER_ID_DEFAULT);
        mOwnerCollege.setText (OWNER_COLLEGE_DEFAULT);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast (obtainContext (), message);
    }

    @Override
    public void showOwner(Owner owner) {
        mProgressPage.setVisibility (View.INVISIBLE);
        mContentPage.setVisibility (View.VISIBLE);
        LogUtil.d (TAG, owner.getName ()+","+owner.getId ()+","+owner.getCollege ());
        if(owner.getName () != null){
            mOwnerName.setText (owner.getName ());
        }else{
            mOwnerName.setText (OWNER_NAME_DEFAULT);
        }
        if(owner.getId () != null){
            mOwnerId.setText (owner.getId ());
        }else{
            mOwnerId.setText (OWNER_ID_DEFAULT);
        }
        if(owner.getCollege () != null){
            mOwnerCollege.setText (owner.getCollege ());
        }else{
            mOwnerCollege.setText (OWNER_COLLEGE_DEFAULT);
        }
    }

    @Override
    public void showPublisher(Publisher publisher) {
        if(publisher.getHeadImage () != null){
            mPublisherHeadImage.setImageBitmap (publisher.getHeadImage ());
        }else{
            mPublisherHeadImage.setImageResource (R.drawable.module_d_head_image_default);
        }
        if(publisher.getName () != null){
            mPublisherName.setText (publisher.getName ());
        }else{
            mPublisherName.setText (PUBLISHER_NAME_DEFAULT);
        }

        // 显示时间,格式为“2019年04月03日11:03”
        mPublishTime.setText (DateFormat.format ("yyyy年MM月dd日HH:mm", publisher.getTime ()));
    }

    @Override
    public void showLoading() {
        assert getFragmentManager () != null;
        mProgressDialog.show (getFragmentManager (), DIALOG_LOAD_TAG);
    }

    @Override
    public void dismissLoading() {
        mProgressDialog.dismiss ();
    }

    @Override
    public void showFailure() {
        assert getFragmentManager () != null;
        mUploadFailDialog.show (getFragmentManager (), DIALOG_FAIL_TAG);
    }

    @Override
    public void dismissFailure() {
        mUploadFailDialog.dismiss ();
    }

    @Override
    public void goToNextPage() {
        mNoSlideViewPager.setCurrentItem (3, true);
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int i = v.getId ();
        if (i == R.id.module_d_fragment_d_b_c_submit) {
            if (mProperty != null) {

                mProperty.setOwnerId (mOwnerId.getText ().toString ());
                mProperty.setOwnerCollege (mOwnerCollege.getText ().toString ());
                mProperty.setOwnerName (mOwnerName.getText ().toString ());

                mProperty.setLostOrFound (LostOrFound.FOUND);
                mFragmentDBCPresenter.upload (mProperty);
            }
        }
    }

    /**
     * {@link FragmentDB.OnArrowClickListener}
     */
    @Override
    public void onLeftClick() {
        //跳转
        mNoSlideViewPager.setCurrentItem (1, true);
    }

    @Override
    public void onRightClick() {
    }

    /**
     * {@link cn.neuwljs.common.dialog.UploadFailDialog.OnButtonClickListener}
     */
    @Override
    public void retry() {
        mFragmentDBCPresenter.retry ();
    }

    @Override
    public void cancelAndSave() {
        mFragmentDBCPresenter.cancelAndSave (mProperty);
    }

    @Subscribe
    public void onLoadEvent(NotifyFragmentDBCLoadOwnerAndPublisherEvent event){

        //加载
        mFragmentDBCPresenter.loadOwner ();
        Publisher publisher = mFragmentDBCPresenter.loadPublisher ();

        mProperty.setPublishTime (publisher.getTime () + "");
        mProperty.setPublisherId (publisher.getId ());
        mProperty.setPublisherCollege (publisher.getCollege ());
        mProperty.setPublisherName (publisher.getName ());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPropertyTypeEvent(NotifyFragmentDBCRefreshPropertyTypeEvent event){

        // 设置失物类型
        if(mProperty != null){
            mProperty.setLostPropertyType (event.getPropertyType ());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPropertyInformationEvent(NotifyFragmentDBCRefreshPropertyInformationEvent event){

        // 设置失物详细信息
        if(mProperty != null){
            mProperty.setInformation (event.getInformation ());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDisplayInformationEvent(NotifyFragmentDBCDisplayInformationEvent event){

        // 显示详细内容
        if(mProperty != null){
            mPublishInformation.setText (event.getInformation ());
        }
    }

    /**
     * editText的监听器,先做两个空实现
     */
    private abstract class MyTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
