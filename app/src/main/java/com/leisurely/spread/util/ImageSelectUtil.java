package com.leisurely.spread.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.view.SelectPicPopupWindow;
import com.leisurely.spread.application.AppApplication;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.widget.camera.CameraActivity;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.Permission;
import com.leisurely.spread.widget.permission.PermissionListener;


/**
 * 图片选择工具
 * 抛弃了之前的单例，解决内存溢出
 * Updata by xyh on 2017/11/17.
 */

public class ImageSelectUtil {

    public static final int PHOTO_REQUEST_CAMERA = 10001;//拍照
    public static final int PHOTO_REQUEST_GALLERY = 20002;//相册中选择
    public static final int PHOTO_REQUEST_CUT = 30003;//截图

    private Uri picUri;
    private SelectPicPopupWindow mMenuWindow;//上传图片用弹出菜单
    private File mCarameFile;//相机拍照临时文件
    private File mCropFile;//裁剪临时文件
    private Activity mActivity;//调用环境
    private ImageView mImageView;//图片控件
    private View rootView;//图片根部局
    private String mFileName;//目标文件
    private boolean mIsNeedCut;//是否需要剪切
    private int status;//popwindow的打开样式
    private boolean mIsNeedCompress = true;//是否需要压缩

    public ImageSelectUtil(Activity activity) {
        this.mActivity = activity;
        this.mFileName = SysConstants.LOCAL_URL + "upload/" + StringUtil.makeRandom(15) + ".jpg";
    }

    public void setmIsNeedCompress(boolean mIsNeedCompress) {
        this.mIsNeedCompress = mIsNeedCompress;
    }

    /**
     * @param imageView 显示图片控件
     * @param isNeedCut 是否需要裁剪
     * @param status    1拍照  2从相册  其他拍照/相册
     */
    public void startSelect(ImageView imageView, boolean isNeedCut, int status) {
        this.mImageView = imageView;
        this.mIsNeedCut = isNeedCut;
        this.status = status;

        //检查sd卡是否可以访问
        new MyPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                realStart();
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                ToastUtil.showToast("没有开启sd卡访问权限！");
            }
        }).request();

    }
    /**
     * @param imageView 显示图片控件
     * @param isNeedCut 是否需要裁剪
     * @param status    1拍照  2从相册  其他拍照/相册
     */
    public void startSelect(ImageView imageView,View rootView, boolean isNeedCut, int status) {
        this.mImageView = imageView;
        this.mIsNeedCut = isNeedCut;
        this.rootView = rootView;
        this.status = status;

        //检查sd卡是否可以访问
        new MyPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                realStart();
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                ToastUtil.showToast("没有开启sd卡访问权限！");
            }
        }).request();

    }

    private void realStart() {
        scanImage(mActivity);
        //实例化popwindo
        mMenuWindow = new SelectPicPopupWindow(mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_myquestion_first:// 相机拍照
                        goCamera();
                        break;

                    case R.id.btn_myquestion_second:// 从相册照片
                        goAlbum();
                        break;

                    case R.id.btn_cancel:// 取消
                        mMenuWindow.dismiss();
                        break;
                }
            }
        }, status);
        mMenuWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    //获取目标文件
    public String getDestFileName() {
        return mFileName;
    }

    public Uri getDestUri(){
        return picUri;
    }
    //获取相机码
    public int getCarameCode() {
        return PHOTO_REQUEST_CAMERA;
    }

    //获取相册码
    public int getAlbumCode() {
        return PHOTO_REQUEST_GALLERY;
    }

    //获取裁剪码
    public int getCropCode() {
        return PHOTO_REQUEST_CUT;
    }

    //扫苗文件夹
    private void scanImage(Activity mActivity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(Environment.getExternalStorageDirectory());
                mediaScanIntent.setData(contentUri);
                mActivity.sendBroadcast(mediaScanIntent);
            } else {
                mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                        + Environment.getExternalStorageDirectory())));
            }
        } catch (Exception e) {

        }
    }

    //检测内存可用
    private boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //生成随机文件
    private File makeRandomFile() {
        File f = new File(SysConstants.LOCAL_URL + "upload/" + StringUtil.makeRandom(15) + ".jpg");
        f.getParentFile().mkdirs();
        return f;
    }

    //前往相册
    private void goAlbum() {
        Intent intent_img = new Intent(Intent.ACTION_PICK);
        intent_img.setType("image/*");
        mActivity.startActivityForResult(intent_img, PHOTO_REQUEST_GALLERY);
    }

    //前往相机拍照
    private void goCamera() {
        new MyPermission(mActivity, Permission.CAMERA, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                Intent intent = new Intent(mActivity, CameraActivity.class);
                if (checkSDCardAvailable()) {
                    mCarameFile = makeRandomFile();
                    intent.putExtra(CameraActivity.NAME_PICTURE, mCarameFile.getPath());
                }
                mActivity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                ToastUtil.makeTextAndShow("未打开相机权限！");
            }
        }).request();
    }

    //前往切图
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            uri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileProvider", new File(uri.getPath()));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        mCropFile = makeRandomFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropFile));
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean save(Uri uri) {
        if (mIsNeedCut) {
            crop(uri);
            return true;
        }
        //mFileName = FilePickUtils.getPath(AppApplication.getInstance(), uri);
        if (mIsNeedCompress) {
            ImageCompressUtil.compressImageToFile(uri, mFileName);
        } else {
//            if (type == 1) {
            mFileName = FilePickUtils.getPath(AppApplication.getInstance(), uri);
//            } else if (type == 2) {
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = mActivity.getContentResolver().query(uri,
//                        filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                mFileName = cursor.getString(columnIndex);  //获取照片路径
//                cursor.close();
//            }
        }
        return false;
    }

    /**
     * 返回是否从activity的setruslt回来的，相册，相机裁剪回来是ture
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @throws Exception
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) throws Exception {
        if (requestCode == PHOTO_REQUEST_GALLERY) {//相册中选择
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                picUri =uri;
                if (save(uri)) {
                    return false;
                }
            }
            return true;
        } else if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {// 拍照
            if (checkSDCardAvailable()) {
                if (save(Uri.fromFile(mCarameFile))) {
                    return false;
                }
            } else {
                ToastUtil.showToast("未找到存储卡，无法存储照片！");
            }
            return true;
        } else if (requestCode == PHOTO_REQUEST_CUT) {//截图
            File file = new File(mFileName);
            file.getParentFile().mkdirs();
            mCropFile.renameTo(file);
            if (mImageView != null) {
                ImageOptions.showImage("file:///" + file.getAbsolutePath(), mImageView);
            }
            try {
                mCarameFile.delete();// 删除临时图片
            } catch (Exception e) {

            }
            return true;
        }
        return false;
    }
}
