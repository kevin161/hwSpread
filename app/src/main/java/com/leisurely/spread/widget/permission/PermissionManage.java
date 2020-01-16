package com.leisurely.spread.widget.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的权限管理
 * Created by xyh on 2017/9/22.
 */

public class PermissionManage implements com.leisurely.spread.widget.permission.PermissionActivity.PermissionListener {

    private Context mContext;
    private int mRequestCode;
    private String[] mPermissions;
    private String[] mDeniedPermissions;
    private com.leisurely.spread.widget.permission.PermissionListener mCallback;

    public PermissionManage(Context context) {
        this.mContext = context;
    }

    @NonNull
    public PermissionManage requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    @NonNull
    public PermissionManage permission(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionManage callback(com.leisurely.spread.widget.permission.PermissionListener callback) {
        this.mCallback = callback;
        return this;
    }

    public void start() {
        //6.0以前默认回调成功
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callbackSucceed();
        } else {
            mDeniedPermissions = getDeniedPermissions(mContext, mPermissions);//返回没有获得的权限
            if (mDeniedPermissions.length > 0) {
                com.leisurely.spread.widget.permission.PermissionActivity.setPermissionListener(this);
                Intent intent = new Intent(mContext, com.leisurely.spread.widget.permission.PermissionActivity.class);
                intent.putExtra(com.leisurely.spread.widget.permission.PermissionActivity.KEY_INPUT_PERMISSIONS, mDeniedPermissions);
                intent.putExtra(com.leisurely.spread.widget.permission.PermissionActivity.KEY_INPUT_REQUEST_CODE, mRequestCode);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                callbackSucceed();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static String[] getDeniedPermissions(Context context, @NonNull String... permissions) {
        List<String> deniedList = new ArrayList<>();
        for (String permission : permissions)
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                deniedList.add(permission);
        return deniedList.toArray(new String[deniedList.size()]);
    }

    private void callbackSucceed() {
        if (mCallback != null) {
            mCallback.onSucceed(mRequestCode, Arrays.asList(mPermissions));
        }
    }

    private void callbackFailed(List<String> deniedList) {
        if (mCallback != null) {
            mCallback.onFailed(mRequestCode, deniedList);
        }
    }

    @Override
    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++)
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                deniedList.add(permissions[i]);

        if (deniedList.isEmpty())
            callbackSucceed();
        else
            callbackFailed(deniedList);
    }
}
