package cn.neuwljs.module_d;

import android.content.Context;

import java.util.Map;

import cn.neuwljs.common.base.IApp;
import cn.neuwljs.ocr.OCR;

import static cn.neuwljs.module_d.Constants.OKHTTP_HEAD_MAP;

/**
 * moduleD的Application
 */
public class ModuleDApplication implements IApp {

    private Context mContext;

    @Override
    public Map<String, String> initNetwork() {

        // ocr初始化，并拿到ocr用到的映射map
        Map<String, String> map = OCR.init (mContext);
        map.putAll (OKHTTP_HEAD_MAP);
        return map;
    }

    @Override
    public void initContext(Context context) {
        mContext = context;
    }
}
