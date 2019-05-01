package cn.neuwljs.module_d.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.neuwljs.module_d.R;

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
        View view = LayoutInflater.from (mContext).inflate (R.layout.module_d_fragment_d_a_banner_view_pager_item, container, false);
        ImageView imageView = view.findViewById (R.id.module_d_fragment_d_a_picture_view_pager_picture);
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
