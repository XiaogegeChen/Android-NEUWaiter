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

/**
 * 显示进度的dialog
 */
public class ProgressDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.dialog_progress, null);
        AppCompatDialog dialog = new AppCompatDialog (getContext (), R.style.BaseDialogStyle);

        // 相关属性
        dialog.setContentView (view);
        dialog.setCancelable (false);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.gravity = Gravity.CENTER;
        params.windowAnimations = android.R.style.Animation_Toast;
        dialog.getWindow ().setAttributes (params);

        return dialog;
    }
}
