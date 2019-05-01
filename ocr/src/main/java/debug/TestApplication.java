package debug;

import android.app.Application;

import java.util.Map;

import cn.neuwljs.network.Network;
import cn.neuwljs.ocr.OCR;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate ();

        Map<String, String> map = OCR.init (getApplicationContext ());

        Network.init (map);
    }
}
