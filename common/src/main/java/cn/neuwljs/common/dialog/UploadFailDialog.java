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
import android.widget.FrameLayout;

import cn.neuwljs.common.R;
import cn.neuwljs.widget.dialog.Widget_MessageDialog;

public class UploadFailDialog extends DialogFragment {

    private OnButtonClickListener mOnButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FrameLayout view = (FrameLayout) LayoutInflater
                .from (getContext ()).inflate (R.layout.common_upload_fail_dialog, null);

        Widget_MessageDialog messageDialog = view.findViewById (R.id.common_upload_fail_dialog);

        if(mOnButtonClickListener != null){
            messageDialog.setListener (new Widget_MessageDialog.OnClickListener () {
                @Override
                public void onCancel() {
                    mOnButtonClickListener.cancelAndSave ();
                }

                @Override
                public void onConfirm() {
                    mOnButtonClickListener.retry ();
                }
            });
        }

        AppCompatDialog dialog = new AppCompatDialog (getContext (), R.style.common_base_dialog_style);

        // 相关配置
        dialog.setContentView (view);
        dialog.setCancelable (false);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.gravity = Gravity.CENTER;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.common_dialog_bottom_anim;
        dialog.getWindow ().setAttributes (params);

        return dialog;
    }

    /**
     * 按钮点击监听
     */
    public interface OnButtonClickListener{
        void retry();
        void cancelAndSave();
    }
}
