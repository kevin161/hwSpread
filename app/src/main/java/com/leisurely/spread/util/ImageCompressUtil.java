package com.leisurely.spread.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.leisurely.spread.application.AppApplication;


public class ImageCompressUtil {

    /**
     * LOG用TAG参数
     */
    private static final String TAG = ImageCompressUtil.class.getSimpleName();

    /**
     * 默认图片压缩宽度 原值480
     */
    public static final int DEFAULT_COMPRESS_WIDTH = 1200;

    /**
     * 默认图片压缩高度 原值800
     */
    public static final int DEFAULT_COMPRESS_HEIGHT = 1200;

    /**
     * 默认图片压缩比
     */
    public static final int DEFAULT_COMPRESS_QUALITY = 80;

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        if (inSampleSize <= 0)
            inSampleSize = 1;
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    private static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只读边,不读内容
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;// 同时设置才会有效
        options.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 读取图片旋转角度
     *
     * @param path
     * @return
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            degree = 0;
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 压缩图片，处理某些手机拍照角度旋转的问题
     *
     * @param context
     * @param srcPath
     * @param destPath
     * @param quality
     * @return
     * @throws FileNotFoundException
     */
    public static void compressImage(Context context, String srcPath, String destPath, int quality)
            throws FileNotFoundException {

        Bitmap bm = getSmallBitmap(srcPath);

        int degree = readPictureDegree(srcPath);

        if (degree != 0) {// 旋转照片角度
            bm = rotateBitmap(bm, degree);
        }

        File f = new File(destPath);
        f.getParentFile().mkdirs();

        FileOutputStream out = new FileOutputStream(destPath);

        bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        bm.recycle();
    }

    public static void compressImageToFile(Uri uri, String destFileName) {
        try {
            ImageCompressUtil.compressImage(AppApplication.getInstance(), FilePickUtils.getPath(AppApplication.getInstance(), uri), destFileName,
                    DEFAULT_COMPRESS_QUALITY);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
