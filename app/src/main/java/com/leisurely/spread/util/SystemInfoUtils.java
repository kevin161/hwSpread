package com.leisurely.spread.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @version V3.0
 * @FileName: com.leisurely.spread.util.SystemInfoUtils.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-13 15:18
 */
public class SystemInfoUtils {

    /**
     * 获取指定包名的版本号
     *
     * @param context
     *            本应用程序上下文
     * @param packageName
     *            你想知道版本信息的应用程序的包名
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context, String packageName)  {

        try {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
        String version = packInfo.versionName;
        return version;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
