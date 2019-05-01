package cn.neuwljs.ocr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import cn.neuwljs.network.Network;

class OCRHelper {

    @SuppressLint("StaticFieldLeak")
    private static volatile OCRHelper sInstance;

    private Context mContext;

    private OCRHelper(Context context){
        mContext = context;
    }

    /**
     * 单例
     */
    static OCRHelper getInstance(Context context){
        if (sInstance == null) {
            synchronized (OCRHelper.class){
                if (sInstance == null) {
                    sInstance = new OCRHelper (context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 通过图片拿到返回的json，如果出错返回错误信息
     * @param bitmap 上传的图片
     * @param callback 回调
     */
    @SuppressLint("CheckResult")
    void recognize(Bitmap bitmap, Callback<Words> callback){

        // 拿到token
        new QueryAccessTokenHelper (mContext)
                .queryData ()
                .filter (accessToken ->
                        accessToken != null && accessToken.getAccessToken () != null)
                .subscribe (accessToken -> {

                    // 拿到accessToken值
                    String accessTokenValue = accessToken.getAccessToken ();

                    // 请求图片文字
                    String imageStr = ImageUtil.encodeImage (bitmap, mContext);

                    if (imageStr != null) {
                        Network.query ()
                                .create (Api.class)
                                .queryOCRWords (accessTokenValue, imageStr)
                                .compose (Network.changeScheduler ())
                                .subscribe (words -> {
                                    if(words != null && words.getWordsResult () != null
                                            && words.getWordsResult ().size () > 0){
                                        callback.onSuccess (words);
                                    }else{
                                        callback.onFailure (new OCRException (100, "recognize failed!"));
                                    }
                                }, e -> {
                                    e.printStackTrace ();
                                    callback.onFailure (new OCRException (100, "unknown error", e));
                                });

                    }
                }, e -> {
                    e.printStackTrace ();
                    callback.onFailure (new OCRException (110, "get access failed!", e));
                });
    }
}
