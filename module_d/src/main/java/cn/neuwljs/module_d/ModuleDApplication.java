package cn.neuwljs.module_d;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import cn.neuwljs.common.base.IApp;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.login.Login;
import cn.neuwljs.ocr.OCR;

import static cn.neuwljs.module_d.Constants.OKHTTP_HEAD_MAP;

/**
 * moduleD的Application
 */
public class ModuleDApplication implements IApp {

    private static final String TAG = "ModuleDApplication";
    private Context mContext;

    @Override
    public Map<String, String> initNetwork() {

        Map<String, String> networkMap = new HashMap<> ();

        // ocr初始化，并拿到ocr用到的映射map
        Map<String, String> ocrMap = OCR.init (mContext);

        // login初始化，并拿到login用到的映射map
        Map<String, String> loginMap = Login.init (mContext);

        networkMap.putAll (ocrMap);
        networkMap.putAll (loginMap);
        networkMap.putAll (OKHTTP_HEAD_MAP);

        return networkMap;
    }

    @Override
    public void initContext(Context context) {
        mContext = context;
        LogUtil.d (TAG, "packageName: " + context.getPackageName ());
    }
}
