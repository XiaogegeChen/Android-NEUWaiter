package com.neuwljs.wallsmalltwo.view.widget.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.neuwljs.wallsmalltwo.R;

public class UploadFailDialog extends DialogFragment implements View.OnClickListener {

    private OnClickListener mOnClickListener;

    public void setListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.fragment_d_b_c_fail_dialog, null);

        // 点击事件
        view.findViewById (R.id.fragment_d_b_c_dialog_cancel).setOnClickListener (this);
        view.findViewById (R.id.fragment_d_b_c_dialog_retry).setOnClickListener (this);

        AppCompatDialog dialog = new AppCompatDialog (getContext (), R.style.BaseDialogStyle);

        // 相关配置
        dialog.setContentView (view);
        dialog.setCancelable (false);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.gravity = Gravity.CENTER;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.DialogBottomAnim;
        dialog.getWindow ().setAttributes (params);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        if(mOnClickListener != null){
            switch (v.getId ()){
                case R.id.fragment_d_b_c_dialog_cancel:
                    mOnClickListener.onCancelClick ();
                    break;
                case R.id.fragment_d_b_c_dialog_retry:
                    mOnClickListener.onRetryClick ();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 按钮点击监听
     */
    public interface OnClickListener{
        void onCancelClick();
        void onRetryClick();
    }
}
