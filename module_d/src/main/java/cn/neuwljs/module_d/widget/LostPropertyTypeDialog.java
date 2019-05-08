package cn.neuwljs.module_d.widget;

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
import android.widget.FrameLayout;

import cn.neuwljs.module_d.R;
import cn.neuwljs.widget.dialog.Widget_MessageListDialog;

public class LostPropertyTypeDialog extends DialogFragment {

    private Widget_MessageListDialog.OnMessageItemClickListener mOnMessageItemClickListener;

    public void setOnMessageItemClickListener(Widget_MessageListDialog.OnMessageItemClickListener onMessageItemClickListener) {
        mOnMessageItemClickListener = onMessageItemClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FrameLayout view = (FrameLayout) LayoutInflater
                .from (getContext ()).inflate (R.layout.module_d_fragment_d_c_lost_property_dialog, null);

        Widget_MessageListDialog messageListDialog = view.findViewById (R.id.module_d_fragment_d_c_lost_property_dialog);

        if (mOnMessageItemClickListener != null) {
            messageListDialog.setOnMessageItemClickListener (mOnMessageItemClickListener);
        }

        AppCompatDialog dialog = new AppCompatDialog (getContext (), cn.neuwljs.common.R.style.common_base_dialog_style);

        // 相关配置
        dialog.setContentView (view);
        dialog.setCancelable (true);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = cn.neuwljs.common.R.style.common_dialog_bottom_anim;
        dialog.getWindow ().setAttributes (params);

        return dialog;
    }
}
