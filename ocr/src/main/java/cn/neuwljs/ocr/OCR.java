package cn.neuwljs.ocr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.Map;

import static cn.neuwljs.ocr.Constant.MAP;

/**
 * ocr门面类
 */
public class OCR {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    /**
     * ocr初始化,在module中的IApp实现类中调用
     * 将这个map放进到主工程的map中，以保证放在
     * {@link cn.neuwljs.network.Network#init(Map)}中
     */
    public static Map<String, String> init(Context context){
        mContext = context;
        return MAP;
    }

    /**
     * 普通识别，返回words对象
     * @param bitmap 待识别的bitmap
     * @param callback 回调
     */
    public static void recognize(Bitmap bitmap, Callback<Words> callback){
        OCRHelper.getInstance (mContext).recognize (bitmap, callback);
    }
}
