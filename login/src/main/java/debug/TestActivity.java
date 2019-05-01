package debug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.neuwljs.login.Callback;
import cn.neuwljs.login.Login;
import cn.neuwljs.login.LoginActivity;
import cn.neuwljs.login.R;
import cn.neuwljs.login.RegisterActivity;
import cn.neuwljs.login.User;

public class TestActivity extends AppCompatActivity implements Callback {

    private static final String TAG = "TestActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.login_activity_test);

        findViewById (R.id.login_test_goto_login).setOnClickListener (v -> {
            Intent intent = new Intent (TestActivity.this, LoginActivity.class);
            startActivity (intent);
        });

        findViewById (R.id.login_test_goto_register).setOnClickListener (v -> {
            Intent intent = new Intent (TestActivity.this, RegisterActivity.class);
            startActivity (intent);
        });

        findViewById (R.id.login_test_judge).setOnClickListener (v -> {
            if (Login.checkLogin () == null) {
                Log.d (TAG, "onCreate: 未登录");
            } else{
                Log.d (TAG, "onCreate: 已登录");
            }
        });

        findViewById (R.id.login_test_goto_mine).setOnClickListener (v -> Login.checkLogin (this));
    }

    /**
     * {@link Callback}
     */
    @Override
    public void onSuccess(User user) {
        Log.d (TAG, "onSuccess: ");
    }

    @Override
    public void onFailure() {
        Log.d (TAG, "onFailure: ");
    }
}
