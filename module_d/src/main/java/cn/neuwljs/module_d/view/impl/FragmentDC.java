package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.common.dialog.ApplyLoginDialog;
import cn.neuwljs.common.dialog.DatePickerDialog;
import cn.neuwljs.common.dialog.TimePickerDialog;
import cn.neuwljs.common.dialog.UploadFailDialog;
import cn.neuwljs.common.dialog.UploadProgressDialog;
import cn.neuwljs.common.dialog.UploadSuccessDialog;
import cn.neuwljs.common.util.ToastUtil;
import cn.neuwljs.login.User;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.model.submit.Property;
import cn.neuwljs.module_d.presenter.impl.FragmentDCPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDCView;
import cn.neuwljs.module_d.widget.LostPropertyTypeDialog;
import cn.neuwljs.widget.dialog.Widget_MessageListDialog;
import cn.neuwljs.widget.layout.Widget_ChooseLayout;
import cn.neuwljs.widget.layout.Widget_MessageLayout;

public class FragmentDC
        extends BaseFragment
        implements IFragmentDCView, View.OnClickListener,
        Widget_MessageListDialog.OnMessageItemClickListener,
        CalendarView.OnDateChangeListener, TimePickerDialog.OnButtonClickListener,
        ApplyLoginDialog.OnButtonClickListener, UploadFailDialog.OnButtonClickListener,
        UploadSuccessDialog.OnButtonClickListener {

    private static final String TAG = "FragmentDC";

    private static final String LOST_PROPERTY_TYPE_DIALOG_TAG = "common_fragment_d_c_lost_property_type_dialog_tag";
    private static final String TIME_PICKER_DIALOG_TAG = "common_fragment_d_c_time_picker_dialog_tag";
    private static final String DATE_PICKER_DIALOG_TAG = "common_fragment_d_c_date_picker_dialog_tag";
    private static final String APPLY_LOGIN_DIALOG_TAG = "common_fragment_d_c_apply_login_dialog_tag";
    private static final String UPLOAD_PROGRESS_DIALOG_TAG = "common_fragment_d_c_upload_progress_dialog_tag";
    private static final String UPLOAD_SUCCESS_DIALOG_TAG = "common_fragment_d_c_upload_success_dialog_tag";
    private static final String UPLOAD_FAIL_DIALOG_TAG = "common_fragment_d_c_upload_fail_dialog_tag";

    // 待提交的bean，逐步赋值
    private Property mProperty;

    private LostPropertyTypeDialog mLostPropertyTypeDialog;
    private TimePickerDialog mTimePickerDialog;
    private DatePickerDialog mDatePickerDialog;
    private ApplyLoginDialog mApplyLoginDialog;
    private UploadProgressDialog mUploadProgressDialog;
    private UploadSuccessDialog mUploadSuccessDialog;
    private UploadFailDialog mUploadFailDialog;

    private FragmentDCPresenterImpl mFragmentDCPresenter;
    private FragmentManager mFragmentManager;

    private Widget_MessageLayout mNameLayout;
    private Widget_MessageLayout mIdLayout;
    private Widget_MessageLayout mCollegeLayout;
    private Widget_MessageLayout mPhoneLayout;
    private TextView mHintText;
    private Widget_ChooseLayout mLostType;
    private Widget_ChooseLayout mLostTime;
    private Widget_ChooseLayout mLostPlace;
    private Widget_ChooseLayout mLostDate;
    private EditText mLostInformation;
    private Button mCommitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDCPresenter.attach (this);
        mFragmentDCPresenter.loadPublisherAndShow ();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDCPresenter.detach ();
    }

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
        mLostDate = view.findViewById (R.id.module_d_fragment_d_c_my_lost_date);
        mLostPlace = view.findViewById (R.id.module_d_fragment_d_c_my_lost_place);
        mLostInformation = view.findViewById (R.id.module_d_fragment_d_c_my_lost_info);
        mCommitButton = view.findViewById (R.id.module_d_fragment_d_c_my_lost_commit);
    }

    @Override
    public void initData() {
        mProperty = new Property ();

        mFragmentManager = getFragmentManager ();
        mFragmentDCPresenter = new FragmentDCPresenterImpl ();

        mLostPropertyTypeDialog = new LostPropertyTypeDialog ();
        mTimePickerDialog = new TimePickerDialog ();
        mDatePickerDialog = new DatePickerDialog ();
        mApplyLoginDialog = new ApplyLoginDialog ();
        mUploadFailDialog = new UploadFailDialog ();
        mUploadProgressDialog = new UploadProgressDialog ();
        mUploadSuccessDialog = new UploadSuccessDialog ();

        mLostType.setOnClickListener (this);
        mLostTime.setOnClickListener (this);
        mLostDate.setOnClickListener (this);
        mLostPlace.setOnClickListener (this);
        mLostPropertyTypeDialog.setOnMessageItemClickListener (this);
        mTimePickerDialog.setOnButtonClickListener (this);
        mDatePickerDialog.setOnDateChangeListener (this);
        mApplyLoginDialog.setOnButtonClickListener (this);
        mUploadFailDialog.setOnButtonClickListener (this);
        mUploadSuccessDialog.setOnButtonClickListener (this);
        mHintText.setOnClickListener (this);
        mCommitButton.setOnClickListener (this);
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();

        if(id == R.id.module_d_fragment_d_c_hint){
            mFragmentDCPresenter.uploadDirectly ();
        }else if(id == R.id.module_d_fragment_d_c_my_lost_type){
            mLostPropertyTypeDialog.show (mFragmentManager, LOST_PROPERTY_TYPE_DIALOG_TAG);
        }else if(id == R.id.module_d_fragment_d_c_my_lost_time){
            mTimePickerDialog.show (mFragmentManager, TIME_PICKER_DIALOG_TAG);
        }else if(id == R.id.module_d_fragment_d_c_my_lost_date){
            mDatePickerDialog.show (mFragmentManager, DATE_PICKER_DIALOG_TAG);
        }else if(id == R.id.module_d_fragment_d_c_my_lost_place){

        }else if(id == R.id.module_d_fragment_d_c_my_lost_commit){

            // TODO 赋值，检查
            mFragmentDCPresenter.upload (mProperty);
        }
    }

    /**
     * {@link cn.neuwljs.widget.dialog.Widget_MessageListDialog.OnMessageItemClickListener}
     */
    @Override
    public void messageItemClick(CharSequence message) {
        mLostPropertyTypeDialog.dismiss ();
        mLostType.getTextView ().setText (message);
    }

    @Override
    public void cancel() {
        mLostPropertyTypeDialog.dismiss ();
    }

    /**
     * {@link android.widget.CalendarView.OnDateChangeListener}
     */
    @Override
    public void onSelectedDayChange(@NotNull CalendarView view, int year, int month, int dayOfMonth) {
        mDatePickerDialog.dismiss ();
        month++;
        String date = year + "-" + month + "-" + dayOfMonth;
        mLostDate.getTextView ().setText (date);
    }

    /**
     * {@link cn.neuwljs.common.dialog.TimePickerDialog.OnButtonClickListener}
     */
    @Override
    public void onConfirm(int hourOfDay, int minute) {
        mTimePickerDialog.dismiss ();
        String time = hourOfDay + ":" + minute;
        mLostTime.getTextView ().setText (time);
    }

    @Override
    public void onCancel() {
        mTimePickerDialog.dismiss ();
    }

    /**
     * {@link cn.neuwljs.common.dialog.ApplyLoginDialog.OnButtonClickListener}
     */
    @Override
    public void gotoLogin() {
        mFragmentDCPresenter.gotoLogin ();
    }

    @Override
    public void cancelLogin() {
        mFragmentDCPresenter.cancelLogin ();
    }

    /**
     * {@link cn.neuwljs.common.dialog.UploadFailDialog.OnButtonClickListener}
     */
    @Override
    public void retry() {

    }

    @Override
    public void cancelAndSave() {

    }

    /**
     * {@link cn.neuwljs.common.dialog.UploadSuccessDialog.OnButtonClickListener}
     */
    @Override
    public void exit() {

    }

    @Override
    public void checkOut() {

    }

    /**
     * {@link IFragmentDCView}
     */

    @Override
    public void showApply() {
        mApplyLoginDialog.show (mFragmentManager, APPLY_LOGIN_DIALOG_TAG);
    }

    @Override
    public void dismissApply() {
        mApplyLoginDialog.dismiss ();
    }

    @Override
    public void showUserInfo(User user) {
        if (user == null) {
            return;
        }
        mNameLayout.getEditText ().setText (user.getName ());
        mIdLayout.getEditText ().setText (user.getStudentId ());
        mCollegeLayout.getEditText ().setText (user.getCollege ());
        mPhoneLayout.getEditText ().setText (user.getPhoneNumber ());
    }

    @Override
    public void showLoading() {
        mUploadProgressDialog.show (mFragmentManager,UPLOAD_PROGRESS_DIALOG_TAG);
    }

    @Override
    public void dismissLoading() {
        mUploadProgressDialog.dismiss ();
    }

    @Override
    public void showFailure() {
        mUploadFailDialog.show (mFragmentManager, UPLOAD_FAIL_DIALOG_TAG);
    }

    @Override
    public void dismissFailure() {
        mUploadFailDialog.dismiss ();
    }

    @Override
    public void showSuccess() {
        mUploadSuccessDialog.show (mFragmentManager, UPLOAD_SUCCESS_DIALOG_TAG);
    }

    @Override
    public void dismissSuccess() {
        mUploadSuccessDialog.dismiss ();
    }

    @Override
    public void showProgressPage() {
    }

    @Override
    public void showErrorPage() {
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast (obtainContext (), message);
    }
}
