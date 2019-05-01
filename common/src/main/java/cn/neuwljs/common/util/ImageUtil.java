package cn.neuwljs.common.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 1280;
    public static final int DEFAULT_QUALITY = 80;

    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    // 裁剪图片
    public static void resize(String inputPath, String outputPath, int dstWidth, int dstHeight, int quality) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options ();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(inputPath, options);
            int inWidth = options.outWidth;
            int inHeight = options.outHeight;
            Matrix m = new Matrix();
            ExifInterface exif = new ExifInterface(inputPath);
            int rotation = exif.getAttributeInt("Orientation", 1);
            if (rotation != 0) {
                m.preRotate((float) exifToDegrees(rotation));
            }
            int maxPreviewImageSize = Math.max(dstWidth, dstHeight);
            int size = Math.min(inWidth, inHeight);
            size = Math.min(size, maxPreviewImageSize);
            options = new BitmapFactory.Options ();
            options.inSampleSize = calculateInSampleSize(options, size, size);
            options.inScaled = true;
            options.inDensity = options.outWidth;
            options.inTargetDensity = size * options.inSampleSize;
            Bitmap roughBitmap = BitmapFactory.decodeFile(inputPath, options);
            FileOutputStream out = new FileOutputStream(outputPath);
            try {
                roughBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeStream (out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 计算缩小比例
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            for(int halfWidth = width / 2; halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth; inSampleSize *= 2) {
                ;
            }
        }
        return inSampleSize;
    }

    // 图片旋转
    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == 6) {
            return 90;
        } else if (exifOrientation == 3) {
            return 180;
        } else {
            return exifOrientation == 8 ? 270 : 0;
        }
    }

    // 图片转byte[]
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    baos.write(buffer, 0, len1);
                }
                return baos.toByteArray();
            } finally {
                closeStream (in);
                closeStream (baos);
            }
        }
    }

    // byte[]转base64
    public static String encode(byte[] from) {
        StringBuilder to = new StringBuilder((int) ((double) from.length * 1.34D) + 3);
        int num = 0;
        char currentByte = 0;
        int i;
        for (i = 0; i < from.length; ++i) {
            for (num %= 8; num < 8; num += 6) {
                switch (num) {
                    case 0:
                        currentByte = (char) (from[i] & lead6byte);
                        currentByte = (char) (currentByte >>> 2);
                    case 1:
                    case 3:
                    case 5:
                    default:
                        break;
                    case 2:
                        currentByte = (char) (from[i] & last6byte);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & last4byte);
                        currentByte = (char) (currentByte << 2);
                        if (i + 1 < from.length) {
                            currentByte = (char) (currentByte | (from[i + 1] & lead2byte) >>> 6);
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & last2byte);
                        currentByte = (char) (currentByte << 4);
                        if (i + 1 < from.length) {
                            currentByte = (char) (currentByte | (from[i + 1] & lead4byte) >>> 4);
                        }
                }
                to.append(encodeTable[currentByte]);
            }
        }
        if (to.length() % 4 != 0) {
            for (i = 4 - to.length() % 4; i > 0; --i) {
                to.append("=");
            }
        }
        return to.toString();
    }

    // 将字符串转换成Bitmap类型
    public static Bitmap decode(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode (string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray (bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return bitmap;
    }

    // 关闭流
    private static void closeStream(Closeable c) {
        if(c != null){
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c = null;
            }
        }
    }

    /**
     * 把一张图片存入指定的文件中
     * @param bitmap 图片
     * @param file 文件
     */
    public static void saveImage(Bitmap bitmap, File file){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream (file);
            bitmap.compress (Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }finally {
            closeStream (fileOutputStream);
        }
    }

    /**
     * 4.4以上版本调用相册时，得到图片的方法
     * @param context 上下文对象
     * @param data 从相册返回的intent
     */
    @TargetApi(19)
    public static Bitmap handleImageOnKitKat(Context context, Intent data){
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

    /**
     * 4.4以上版本调用相册时，得到图片的方法
     * @param context 上下文对象
     * @param data 从相册返回的intent
     */
    public static Bitmap handleImageBeforeKitkat(Context context, Intent data){
        Uri uri = data.getData ();
        String imagePath = getImagePath (context, uri,null);

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
