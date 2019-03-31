package com.neuwljs.wallsmalltwo.common;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (getLayoutId (), container, false);
        init (view);
        return view;
    }

    /**
     * 提供一个获取context的方法
     * @return context
     */
    public Context obtainContext(){
        return mContext;
    }

    /**
     * 提供一个获取Activity的方法
     * @return Activity
     */
    public FragmentActivity obtainActivity(){
        return (FragmentActivity)mContext;
    }

    /**
     * 提供一个拿到资源文件的方法
     * @return 昂前fragment的资源文件
     */
    public Resources obtainResources(){
        return mContext.getResources ();
    }

    private void init(View view){
        initView (view);
        initData ();
    }

    public abstract int getLayoutId();

    public abstract void initView(View view);

    public abstract void initData();
}
