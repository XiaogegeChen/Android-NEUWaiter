package cn.neuwljs.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neuwljs.widget.R;

/**
 * 消息对话框
 */
public class Widget_MessageDialog extends LinearLayout implements View.OnClickListener {

    private OnClickListener mOnClickListener;

    public Widget_MessageDialog(Context context) {
        this (context, null);
    }

    public Widget_MessageDialog(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public void setListener(OnClickListener listener){
        mOnClickListener = listener;
    }

    public Widget_MessageDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_message_dialog, this);

        // 拿到控件
        TextView title = findViewById (R.id.widget_message_dialog_title);
        TextView content = findViewById (R.id.widget_message_dialog_content);
        TextView cancel = findViewById (R.id.widget_message_dialog_cancel);
        TextView confirm = findViewById (R.id.widget_message_dialog_confirm);

        cancel.setOnClickListener (this);
        confirm.setOnClickListener (this);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_MessageDialog);

        // 拿到属性
        String titleText = typedArray.getString (R.styleable.Widget_MessageDialog_widget_message_title);
        String contentText = typedArray.getString (R.styleable.Widget_MessageDialog_widget_message_content);
        String cancelText = typedArray.getString (R.styleable.Widget_MessageDialog_widget_message_cancel);
        String confirmText = typedArray.getString (R.styleable.Widget_MessageDialog_widget_message_confirm);

        // 设置到布局
        title.setText (titleText);
        content.setText (contentText);
        cancel.setText (cancelText);
        confirm.setText (confirmText);

        // 回收资源
        typedArray.recycle ();
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if(mOnClickListener == null){
            return;
        }

        int id = v.getId ();
        if(id == R.id.widget_message_dialog_cancel){
            mOnClickListener.onCancel ();
        }else if(id == R.id.widget_message_dialog_confirm){
            mOnClickListener.onConfirm ();
        }
    }

    /**
     * 按钮点击监听
     */
    public interface OnClickListener{
        /**
         * 点击取消按钮的监听器
         */
        void onCancel();

        /**
         * 点击确定按钮的监听器
         */
        void onConfirm();
    }
}
