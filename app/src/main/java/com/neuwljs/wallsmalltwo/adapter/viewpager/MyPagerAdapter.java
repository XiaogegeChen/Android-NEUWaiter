package com.neuwljs.wallsmalltwo.adapter.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neuwljs.wallsmalltwo.R;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Bitmap> mBitmapList;

    public MyPagerAdapter(Context context, List<Bitmap> bitmapList) {
        mContext = context;
        mBitmapList = bitmapList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from (mContext).inflate (R.layout.fragment_d_a_picture_view_pager_item, container, false);
        ImageView imageView = view.findViewById (R.id.fragment_d_a_picture_view_pager_picture);
        imageView.setImageBitmap (mBitmapList.get (position));
        container.addView (view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView ((View)object);
    }

    @Override
    public int getCount() {
        return mBitmapList.size ();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
