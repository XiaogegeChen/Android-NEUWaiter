package cn.neuwljs.photo;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * 该模块的图片处理类
 */
class ImageUtil {

    /**
     * 4.4以上版本调用相册时，得到图片的方法
     * @param context 上下文对象
     * @param data 从相册返回的intent
     */
    static Bitmap handleImageOnKitKat(Context context, Intent data){
        Uri uri = data.getData ();
        String imagePath = null;
        if(DocumentsContract.isDocumentUri (context,uri)){
            String docId = DocumentsContract.getDocumentId (uri);
            assert uri != null;
            if("com.android.providers.media.documents".equals (uri.getAuthority ())){
                String id = docId.split (":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath (context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.download.documents".equals (uri.getAuthority ())){
                Uri contentUri = ContentUris.withAppendedId (
                        Uri.parse ("content://downloads/public_downloads"),Long.valueOf (docId));
                imagePath = getImagePath (context, contentUri,null);
            }
        }else {
            assert uri != null;
            if("content".equalsIgnoreCase (uri.getScheme ())){
                imagePath = getImagePath (context, uri,null);
            }else if("file".equalsIgnoreCase (uri.getScheme ())){
                imagePath = uri.getPath ();
            }
        }

        return BitmapFactory.decodeFile (imagePath);
    }

    private static String getImagePath(Context context, Uri uri,String selection){
        String path = null;
        Cursor cursor = context.getContentResolver ().query (uri,null,selection
                ,null,null);
        if(cursor != null){
            if(cursor.moveToFirst ()){
                path = cursor.getString (cursor.getColumnIndex (MediaStore.Images.Media.DATA));
            }
            cursor.close ();
        }
        return path;
    }
}
