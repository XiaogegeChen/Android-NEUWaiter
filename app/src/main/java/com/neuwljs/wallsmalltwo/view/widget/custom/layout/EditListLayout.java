package com.neuwljs.wallsmalltwo.view.widget.custom.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neuwljs.wallsmalltwo.R;

import androidx.annotation.Nullable;

/**
 * 自定义一个用于显示列表的layout,包含一个图片和一个输入框
 */
public class EditListLayout extends LinearLayout {

    private EditText mEditText;

    public EditListLayout(Context context) {
        this (context, null);
    }

    public EditListLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public EditListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.edit_list_layout, this);

        // 拿到控件
        ImageView icon = findViewById (R.id.edit_list_layout_image_view);
        mEditText = findViewById (R.id.edit_list_layout_edit_text);

        // 拿到资源和属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.EditListLayout);

        int imageId = typedArray.getResourceId (R.styleable.EditListLayout_editIcon, R.drawable.ic_list_share);
        String hintText = typedArray.getString (R.styleable.EditListLayout_hint);
        int textColor = typedArray.getColor (R.styleable.EditListLayout_textColor, Color.BLACK);
        int inputType = typedArray.getInt (R.styleable.EditListLayout_inputType, 0);

        // 设置属性
        icon.setImageResource (imageId);
        mEditText.setHint (hintText);
        mEditText.setTextColor (textColor);

        switch (inputType){
            case 2:
                mEditText.setInputType (InputType.TYPE_CLASS_NUMBER);
                break;
            case 1:
                mEditText.setInputType (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            default:
                mEditText.setInputType (InputType.TYPE_CLASS_TEXT);
                break;
        }

        // 回收资源
        typedArray.recycle ();
    }

    // 提供一个拿到edit的公开方法
    public EditText getEditText(){
        return mEditText;
    }
}
