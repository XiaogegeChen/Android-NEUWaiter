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
import android.widget.CalendarView;
import android.widget.FrameLayout;

import cn.neuwljs.common.R;

public class DatePickerDialog extends DialogFragment {

    private CalendarView.OnDateChangeListener mOnDateChangeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FrameLayout view = (FrameLayout) LayoutInflater
                .from (getContext ()).inflate (R.layout.common_date_picker_dialog, null);

        CalendarView calendarView = view.findViewById (R.id.common_date_picker_dialog_calendar_view);

        if (mOnDateChangeListener != null) {
            calendarView.setOnDateChangeListener (mOnDateChangeListener);
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

    public void setOnDateChangeListener(CalendarView.OnDateChangeListener onDateChangeListener) {
        mOnDateChangeListener = onDateChangeListener;
    }
}
