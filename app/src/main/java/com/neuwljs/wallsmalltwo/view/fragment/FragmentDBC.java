package com.neuwljs.wallsmalltwo.view.fragment;

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

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.IndicatorFragment;
import com.neuwljs.wallsmalltwo.model.base.LostOrFound;
import com.neuwljs.wallsmalltwo.model.base.Owner;
import com.neuwljs.wallsmalltwo.model.base.Publisher;
import com.neuwljs.wallsmalltwo.model.submit.Property;
import com.neuwljs.wallsmalltwo.presenter.impl.FragmentDBCPresenterImpl;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.StringUtil;
import com.neuwljs.wallsmalltwo.util.ToastUtil;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.widget.NoSlideViewPager;
import com.neuwljs.wallsmalltwo.view.widget.dialog.ProgressDialog;
import com.neuwljs.wallsmalltwo.view.widget.dialog.UploadFailDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neuwljs.wallsmalltwo.model.Constant.COLLEGE_MAX_LENGTH;
import static com.neuwljs.wallsmalltwo.model.Constant.ID_MAX_LENGTH;
import static com.neuwljs.wallsmalltwo.model.Constant.NAME_MAX_LENGTH;
import static com.neuwljs.wallsmalltwo.model.Constant.OWNER_COLLEGE_DEFAULT;
import static com.neuwljs.wallsmalltwo.model.Constant.OWNER_ID_DEFAULT;
import static com.neuwljs.wallsmalltwo.model.Constant.OWNER_NAME_DEFAULT;
import static com.neuwljs.wallsmalltwo.model.Constant.PUBLISHER_NAME_DEFAULT;

public class FragmentDBC
        extends IndicatorFragment
        implements View.OnClickListener, ViewContract.FragmentDBCView,
        FragmentDB.OnArrowClickListener, UploadFailDialog.OnClickListener {

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
    private ProgressDialog mProgressDialog;

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
        return R.layout.fragment_d_b_c;
    }

    @Override
    public void initView(View view) {
        mSubmitButton = view.findViewById (R.id.fragment_d_b_c_submit);
        mProgressPage = view.findViewById (R.id.fragment_d_b_c_progress_page);
        mContentPage = view.findViewById (R.id.fragment_d_b_c_content_page);
        mPublisherHeadImage = view.findViewById (R.id.fragment_d_b_c_publisher_head_image);
        mPublisherName = view.findViewById (R.id.fragment_d_b_c_publisher_name);
        mPublishTime = view.findViewById (R.id.fragment_d_b_c_publisher_time);
        mPublishInformation = view.findViewById (R.id.fragment_d_b_c_publisher_information);
        mOwnerName = view.findViewById (R.id.fragment_d_b_c_owner_name);
        mOwnerId = view.findViewById (R.id.fragment_d_b_c_owner_id);
        mOwnerCollege = view.findViewById (R.id.fragment_d_b_c_owner_college);
        mOwnerNameEdit = view.findViewById (R.id.fragment_d_b_c_owner_name_edit);
        mOwnerIdEdit = view.findViewById (R.id.fragment_d_b_c_owner_id_edit);
        mOwnerCollegeEdit = view.findViewById (R.id.fragment_d_b_c_owner_college_edit);
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

        mProgressDialog = new ProgressDialog ();
        mUploadFailDialog = new UploadFailDialog ();
        mUploadFailDialog.setListener (this);

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
        mPublisherHeadImage.setImageResource (R.drawable.ic_default);
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
            mPublisherHeadImage.setImageResource (R.drawable.ic_default);
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
        switch (v.getId ()){
            case R.id.fragment_d_b_c_submit:
                if(mProperty != null){

                    mProperty.setOwnerId (mOwnerId.getText ().toString ());
                    mProperty.setOwnerCollege (mOwnerCollege.getText ().toString ());
                    mProperty.setOwnerName (mOwnerName.getText ().toString ());

                    mProperty.setLostOrFound (LostOrFound.FOUND);
                    mFragmentDBCPresenter.upload (mProperty);
                }
                break;
            default:
                break;
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

    @Subscribe
    public void onLoadEvent(LoadEvent event){

        //加载
        if(event.isBegin ()){
            mFragmentDBCPresenter.loadOwner ();
            Publisher publisher = mFragmentDBCPresenter.loadPublisher ();

            mProperty.setPublishTime (publisher.getTime () + "");
            mProperty.setPublisherId (publisher.getId ());
            mProperty.setPublisherCollege (publisher.getCollege ());
            mProperty.setPublisherName (publisher.getName ());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPropertyTypeEvent(RefreshPropertyTypeEvent event){

        // 设置失物类型
        if(mProperty != null){
            mProperty.setLostPropertyType (event.getPropertyType ());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPropertyInformationEvent(RefreshPropertyInformationEvent event){

        // 设置失物详细信息
        if(mProperty != null){
            mProperty.setInformation (event.getInformation ());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDisplayInformationEvent(DisplayInformationEvent event){

        // 显示详细内容
        if(mProperty != null){
            mPublishInformation.setText (event.getInformation ());
        }
    }

    /**
     * {@link com.neuwljs.wallsmalltwo.view.widget.dialog.UploadFailDialog.OnClickListener}
     */
    @Override
    public void onCancelClick() {
        mFragmentDBCPresenter.cancelAndSave (mProperty);
    }

    @Override
    public void onRetryClick() {
        mFragmentDBCPresenter.retry ();
    }

    /**
     * 通知该碎片进行加载Owner和Publisher的事件类
     */
    public static class LoadEvent{
        /**
         * 是否开始加载Owner和Publisher
         */
        private boolean begin;

        public boolean isBegin() {
            return begin;
        }

        public void setBegin(boolean begin) {
            this.begin = begin;
        }
    }

    /**
     * 通知该碎片刷新失物类型的事件类
     */
    public static class RefreshPropertyTypeEvent{

        /**
         * 失物类型
         */
        private String propertyType;

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }
    }

    /**
     * 通知该碎片刷新失物描述的事件类
     */
    public static class RefreshPropertyInformationEvent{

        /**
         * 失物的详细描述
         */
        private String information;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }
    }

    /**
     * 通知该碎片显示详细信息的事件类
     */
    public static class DisplayInformationEvent{

        /**
         * 失物的详细描述
         */
        private String information;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
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
