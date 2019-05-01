package cn.neuwljs.module_d.view;

import android.graphics.Bitmap;

import cn.neuwljs.common.base.IBaseView;

public interface IFragmentDBBView extends IBaseView {
    /**
     * 显示拍照或者从相册拿到的图片
     * @param bitmap 拿到的图片
     */
    void showImage(Bitmap bitmap);
}
