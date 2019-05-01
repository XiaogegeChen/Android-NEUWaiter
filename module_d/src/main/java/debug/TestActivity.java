package debug;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.view.impl.FragmentD;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.module_d_activity_test);

        replace (new FragmentD ());

    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager ().beginTransaction ();
        transaction.replace (R.id.module_d_test_activity_content, fragment);
        transaction.commit ();
    }
}
