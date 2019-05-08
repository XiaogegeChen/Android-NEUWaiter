package cn.neuwljs.common.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import cn.neuwljs.common.R;

public class TimePickerDialog extends DialogFragment {

    private OnButtonClickListener mOnButtonClickListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LinearLayout view = (LinearLayout) LayoutInflater
                .from (getContext ()).inflate (R.layout.common_time_picker_dialog, null);

        TextView cancel = view.findViewById (R.id.common_time_picker_dialog_cancel);
        TextView confirm = view.findViewById (R.id.common_time_picker_dialog_confirm);

        TimePicker timePicker = view.findViewById (R.id.common_time_picker_dialog_time_picker);
        timePicker.setIs24HourView (true);

        if (mOnButtonClickListener != null) {
            cancel.setOnClickListener (v -> mOnButtonClickListener.onCancel ());
            confirm.setOnClickListener (v -> mOnButtonClickListener.onConfirm (timePicker.getCurrentHour (), timePicker.getCurrentMinute ()));
        }

        AppCompatDialog dialog = new AppCompatDialog (getContext (), R.style.common_base_dialog_style);

        // 相关配置
        dialog.setContentView (view);
        dialog.setCancelable (true);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.common_dialog_bottom_anim;
        dialog.getWindow ().setAttributes (params);

        return dialog;

    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    /**
     * 按钮点击回调
     */
    public interface OnButtonClickListener{
        void onConfirm(int hourOfDay, int minute);
        void onCancel();
    }
}
