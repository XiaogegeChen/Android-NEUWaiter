package debug;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import cn.neuwljs.login.Login;
import cn.neuwljs.network.Network;
import cn.neuwljs.ocr.OCR;

import static cn.neuwljs.module_d.Constants.OKHTTP_HEAD_MAP;

public class DebugApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate ();

        Map<String, String> networkMap = new HashMap<> ();

        Map<String, String> ocrMap = OCR.init (getApplicationContext ());
        Map<String, String> loginMap = Login.init (getApplicationContext ());

        networkMap.putAll (OKHTTP_HEAD_MAP);
        networkMap.putAll (ocrMap);
        networkMap.putAll (loginMap);

        Network.init (networkMap);
    }
}