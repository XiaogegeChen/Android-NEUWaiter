package debug;

import android.app.Application;

import java.util.Map;

import cn.neuwljs.login.Login;
import cn.neuwljs.network.Network;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate ();

        Map<String, String> map = Login.init (getApplicationContext ());
        Network.init (map);
    }
}
