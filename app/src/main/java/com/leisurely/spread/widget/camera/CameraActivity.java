package com.leisurely.spread.widget.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leisurely.spread.R;


/**
 * 相机拍照界面,工具类，不必继承基类
 * xyh 2017/9/22
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    private CameraView mCameraView;//拍照显示界面
    private ImageView mIvShowPhoto;//拍完照显示图片
    private RelativeLayout mRlTakePhoto;//拍照时显示
    private LinearLayout mLlShowPhoto;//拍完照显示
    private CircleOnCamera mCircleOnCamera;//焦点显示

    private ImageView mIvTakePhoto;//拍照点击
    private RelativeLayout mRlBack;//返回点击
    private ImageView mIvTakeBack;//继续拍照点击点击
    private ImageView mIvConfirm;//确认图片点击

    public static final String NAME_PICTURE = "NAME_PICTURE";//图片保存的文件名
    private String mFileName;
    private Bitmap mBitmap;

    private boolean isShowCircle = true;//是否显示焦点圆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        mFileName = getIntent().getStringExtra(NAME_PICTURE);

        initView();
        initEvent();

    }

    private void initView() {
        mCameraView = (CameraView) findViewById(R.id.mCameraView);
        mIvShowPhoto = (ImageView) findViewById(R.id.mIvShowPhoto);
        mRlTakePhoto = (RelativeLayout) findViewById(R.id.mRlTakePhoto);
        mLlShowPhoto = (LinearLayout) findViewById(R.id.mLlShowPhoto);
        mCircleOnCamera = (CircleOnCamera) findViewById(R.id.mRectOnCamera);

        mIvTakePhoto = (ImageView) findViewById(R.id.mIvTakePhoto);
        mIvTakeBack = (ImageView) findViewById(R.id.mIvTakeBack);
        mIvConfirm = (ImageView) findViewById(R.id.mIvConfirm);
        mRlBack = (RelativeLayout) findViewById(R.id.mRlBack);
    }

    private void initEvent() {
        mIvTakePhoto.setOnClickListener(this);
        mIvTakeBack.setOnClickListener(this);
        mIvConfirm.setOnClickListener(this);
        mRlBack.setOnClickListener(this);

        mCameraView.setBitmap(new CameraView.CameraCall() {
            @Override
            public void show(Bitmap bitmap, boolean isVertical) {
                mBitmap = bitmap;

                mLlShowPhoto.setVisibility(View.VISIBLE);
                mRlTakePhoto.setVisibility(View.INVISIBLE);
                mIvShowPhoto.setVisibility(View.VISIBLE);

                if (isVertical) {
                    mIvShowPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    mIvShowPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                mIvShowPhoto.setImageBitmap(bitmap);
            }

            @Override
            public boolean hasFind(float x, float y) {
                if (y > mRlTakePhoto.getTop()) {
                    return false;
                }
                if (isShowCircle)
                    mCircleOnCamera.update();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.registerSensorManager(this);//注册方向感应器
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.unregisterSensorManager(this);//反注册方向感应器
    }

    //把bitmap用作图片保存
    private void save() {
        if (mBitmap != null && mFileName.length() > 0) {
            CameraParamUtil.getInstance().saveBitmap(mFileName, mBitmap);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mIvTakePhoto:
                isShowCircle = false;
                mCameraView.takePicture();
                break;
            case R.id.mRlBack:
                finish();
                break;
            case R.id.mIvTakeBack:
                isShowCircle = true;
                mLlShowPhoto.setVisibility(View.INVISIBLE);
                mRlTakePhoto.setVisibility(View.VISIBLE);
                mIvShowPhoto.setVisibility(View.INVISIBLE);
                break;
            case R.id.mIvConfirm:
                setResult(RESULT_OK);
                save();
                finish();
                break;
        }
    }

}
