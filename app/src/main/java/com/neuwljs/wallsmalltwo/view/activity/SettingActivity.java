package com.neuwljs.wallsmalltwo.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseActivity;
import com.neuwljs.wallsmalltwo.view.fragment.SettingFragmentD;

import static com.neuwljs.wallsmalltwo.model.Constant.IntentConstants.INTENT_PARAM_FROM_FRAGMENTDD_TO_SETTINGACTIVITY;
import static com.neuwljs.wallsmalltwo.model.Constant.IntentConstants.INTENT_PARAM_STRING_KEY;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent ();
        String param = intent.getStringExtra (INTENT_PARAM_STRING_KEY);

        switch (param){
            case INTENT_PARAM_FROM_FRAGMENTDD_TO_SETTINGACTIVITY:
                replace (new SettingFragmentD ());
                break;
            default:
                throw new IllegalArgumentException ("未指定fragment");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public int getStatusBarColor() {
        return getContext ().getResources ().getColor (R.color.colorAccent);
    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager ().beginTransaction ();
        transaction.replace (R.id.activity_setting_fragment, fragment);
        transaction.commit ();
    }
}
