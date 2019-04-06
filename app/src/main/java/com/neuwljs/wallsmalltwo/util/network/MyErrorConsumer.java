package com.neuwljs.wallsmalltwo.util.network;

import android.content.Context;

import com.neuwljs.wallsmalltwo.model.MyException;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.ToastUtil;

import io.reactivex.functions.Consumer;

import static com.neuwljs.wallsmalltwo.util.ExceptionUtil.UNKNOWN_EXCEPTION;

/**
 * 对抛出的异常进行统一处理
 * 如果是MyException就弹出Toast提示ExceptionCode,如果是别的异常就弹出Toast提示未知错误
 */
public class MyErrorConsumer implements Consumer<Throwable> {

    private static final String TAG = "MyErrorConsumer";

    private Context mContext;

    public MyErrorConsumer(Context context) {
        mContext = context;
    }

    @Override
    public void accept(Throwable e) throws Exception {

        // 打印原信息
        e.printStackTrace ();
        LogUtil.d (TAG, e.getMessage ());

        // 显示Toast
        if(e instanceof MyException){
            ToastUtil.showToast (mContext, ((MyException) e).getExceptionCode ());
        }else{
            ToastUtil.showToast (mContext, UNKNOWN_EXCEPTION);
        }
    }
}
