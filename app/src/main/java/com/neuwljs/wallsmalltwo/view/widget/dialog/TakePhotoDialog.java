package com.neuwljs.wallsmalltwo.view.widget.dialog;

import android.app.Dialog;
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

public class TakePhotoDialog extends DialogFragment implements View.OnClickListener {

    private OnClickListener mListener;

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.fragment_d_b_b_dialog, null);

        //点击事件
        view.findViewById (R.id.fragment_d_b_b_camera).setOnClickListener (this);
        view.findViewById (R.id.fragment_d_b_b_album).setOnClickListener (this);
        view.findViewById (R.id.fragment_d_b_b_cancel).setOnClickListener (this);

        AppCompatDialog appCompatDialog = new AppCompatDialog (getContext (), R.style.BaseDialogStyle);

        // 配置相关设置
        appCompatDialog.setContentView (view);
        appCompatDialog.setCancelable (true);
        WindowManager.LayoutParams params = appCompatDialog.getWindow ().getAttributes ();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.DialogBottomAnim;
        appCompatDialog.getWindow ().setAttributes (params);

        return appCompatDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fragment_d_b_b_camera:
                mListener.onCameraClick ();
                break;
            case R.id.fragment_d_b_b_album:
                mListener.onAlbumClick ();
                break;
            case R.id.fragment_d_b_b_cancel:
                mListener.onCancelClick ();
                break;
            default:
                break;
        }
    }

    public interface OnClickListener{
        void onCancelClick();
        void onCameraClick();
        void onAlbumClick();
    }
}
