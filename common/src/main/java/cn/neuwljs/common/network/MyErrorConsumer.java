package cn.neuwljs.common.network;

import android.content.Context;

import cn.neuwljs.common.util.ToastUtil;
import io.reactivex.functions.Consumer;

import static cn.neuwljs.common.network.ExceptionUtil.UNKNOWN_EXCEPTION;

/**
 * 对抛出的异常进行统一处理
 * 如果是MyException就弹出Toast提示ExceptionCode,如果是别的异常就弹出Toast提示未知错误
 */
public class MyErrorConsumer implements Consumer<Throwable> {

    private static final String TAG = "MyErrorConsumer";

    /**
     * 上下文对象
     */
    private Context mContext;

    private OnErrorListener mErrorListener;

    public MyErrorConsumer(Context context) {
        mContext = context;
    }

    public MyErrorConsumer(Context context, OnErrorListener listener) {
        mContext = context;
        mErrorListener = listener;
    }

    @Override
    public void accept(Throwable e) throws Exception {

        // 打印原信息
        e.printStackTrace ();

        // 显示Toast
        if(e instanceof MyException){
            ToastUtil.showToast (mContext, ((MyException) e).getExceptionCode ());
        }else{
            ToastUtil.showToast (mContext, UNKNOWN_EXCEPTION);
        }

        // 如果调用者有自己的处理，也要执行
        if(mErrorListener != null){
            mErrorListener.onError ();
        }
    }

    /**
     * 具体调用者对error的处理
     */
    public interface OnErrorListener{
        void onError();
    }
}
