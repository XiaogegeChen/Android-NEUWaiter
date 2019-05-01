package cn.neuwljs.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;

/**
 * 拍照和选择图片功能的帮助类封装
 * 使用方法:
 * 1.调用静态方法{@link #getPhoto(FragmentManager, Callback)}
 * 2.在activity中重写activity的{@link android.app.Activity#onActivityResult(int, int, Intent)}
 *    并一定要继承父类的onActivityResult()
 * 3.通过{@link FragmentManager#findFragmentByTag(String)}方法并传入{@link #sTag}
 *   参数找到对话框fragment并调用这个fragment的{@link android.support.v4.app.Fragment#onActivityResult(int, int, Intent)}
 *   方法将拍照之后的回调传给对话框fragment
 */
public class PhotoHelper {

    // 弹出的dialogFragment的tag
    private static String sTag = "photo_take_photo";

    public static void getPhoto(final FragmentManager manager, final Callback callback){

        // 1.弹出对话框
        final TakePhotoDialog dialog = new TakePhotoDialog ();
        dialog.setListener (new TakePhotoDialog.OnClickListener () {
            @Override
            public void onCancelClick() {

                // 点击弹窗的取消按钮，直接回调onCancel取消
                dialog.dismiss ();
                callback.onCancel ();
            }

            @Override
            public void onCameraClick() {

                // 2.如果点击的是拍照，拍照
                dialog.prepareTakePhoto (callback);
            }

            @Override
            public void onAlbumClick() {

                // 3.如果点击的是相册
                dialog.prepareChoosePhoto (callback);
            }
        });

        dialog.show (manager, sTag);
    }

    /**
     * 向外界提供弹出的dialog的tag
     * @return 弹出的dialog的tag
     */
    public static String getDialogTag(){
        return sTag;
    }

    /**
     * 结果回调
     */
    public interface Callback{
        /**
         * 点击取消键的回调
         */
        void onCancel();

        /**
         * 拍照成功的回调
         * @param bitmap 拍照得到的照片
         */
        void onTakePhotoSuccess(Bitmap bitmap);

        /**
         * 拍照失败的回调
         */
        void onTakePhotoFailure();

        /**
         * 选择照片成功的回调
         * @param bitmap 选择的照片
         */
        void onChoosePhotoSuccess(Bitmap bitmap);

        /**
         * 选择照片失败的回调
         */
        void onChoosePhotoFailure();
    }
}
