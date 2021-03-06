package cn.neuwljs.ocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class ImageUtil {

    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};


    /**
     * 对图片进行base64编码
     * @param bitmap 待编码图片
     * @return 改图片的base64编码结果
     */
    static String encodeImage(Bitmap bitmap, Context context) {

        // 如果没有这个图,直接返回null
        if (bitmap == null) {
            return null;
        }

        File bitmapFolder = new File (context.getExternalFilesDir (null), "Bitmap");
        if(!bitmapFolder.exists ()){
            bitmapFolder.mkdirs ();
        }

        File file = new File (bitmapFolder, "ocr_bitmap.jpg");
        if(file.exists ()){
            file.delete ();
        }
        try {
            file.createNewFile ();
        } catch (IOException e) {
            e.printStackTrace ();
            return null;
        }
        saveImage (bitmap, file);

        File fileTemp = new File (bitmapFolder, "ocr_bitmap_temp.jpg");
        if(fileTemp.exists ()){
            fileTemp.delete ();
        }
        try {
            fileTemp.createNewFile ();
        } catch (IOException e) {
            e.printStackTrace ();
            return null;
        }

        resize (file.getPath (), fileTemp.getPath (), 1280, 1280, 80);

        // 拿到流
        byte[] imgData = null;
        try {
            imgData = readFileByBytes (fileTemp.getPath ());
        } catch (IOException e) {
            e.printStackTrace ();
            return null;
        }

        // 拿到base64字符串并返回
        String imgStr = null;
        if(imgData != null){
            imgStr = encode (imgData);
            imgStr = imgStr.replace("data:image/jpg;base64,", "");
        }

        return imgStr;
    }

    // 裁剪图片
    private static void resize(String inputPath, String outputPath, int dstWidth, int dstHeight, int quality) {
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

    /**
     * 把一张图片存入指定的文件中
     * @param bitmap 图片
     * @param file 文件
     */
    private static void saveImage(Bitmap bitmap, File file){
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

    // 图片转byte[]
    private static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream (file));
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
    private static String encode(byte[] from) {
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
}
