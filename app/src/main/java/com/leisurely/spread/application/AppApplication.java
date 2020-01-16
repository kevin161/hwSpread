
package com.leisurely.spread.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.leisurely.spread.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AppApplication extends Application {

    private static final String TAG = "App";

    private  static String userId;
    private static String token;
    private static String phone;
    private static boolean isLogin;

    private static String mEnterTime = "";

    public static List<Integer> mListPosition = new ArrayList<Integer>();
    public static final String imgPath = Environment.getExternalStorageDirectory() + "/shy/imagecache";
    /**
     * 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了
     */
    private static AppApplication instance;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;
    private static Context sContext;
    private String mVersion = null;
    private Handler mHandler = new Handler();
    public static HashMap<String, String> userMap = new HashMap<String, String>();
    public static IWXAPI msgApi;
    public static UploadManager uploadManager;

    /**
     * 数据库
     */
//    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase db;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;

//    public static TtsUtils ttsUtils;
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        } else {
//            LeakCanary.install(this);
//        }

        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
        sContext = this;
        instance = this;
        //  Utils.init(sContext);
//        mMainThreadId = android.os.Process.myTid();
//        mMainThread = Thread.currentThread();
//        mMainThreadHandler = new Handler();
//        mMainLooper = getMainLooper();
        //初始化图片加载工厂
        initImageFactory(sContext);

//        Configuration config = new Configuration.Builder()
//                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
//                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
//                .connectTimeout(10)           // 链接超时。默认10秒
//                .useHttps(true)               // 是否使用https上传域名
//                .responseTimeout(60)          // 服务器响应超时。默认60秒
////                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
////                    .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
//                .build();
//        uploadManager = new UploadManager(config);
        //注册微信支付
        // 将该app注册到微信
//        final IWXAPI msgApi = WXAPIFactory.createWXAPI(sContext, null);
//        msgApi.registerApp(SysConstants.weChatAppId);
//           PayWXinImpl.register(this);
//        initSign();
//        initLocation(sContext);
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush
        //数据库
//        setDatabase();

//        Context context = getApplicationContext();
//        // 获取当前包名
//        String packageName = context.getPackageName();
//        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        // 初始化Bugly    发布时把第三个参数改为false

//        Fresco.initialize(this); // 初始化Fresco

        //测试
//        Bugly.init(getApplicationContext(), "584a503b27", false);

        //正式库
        Bugly.init(getApplicationContext(), "48bdd98af2", false);


//        msgApi = WXAPIFactory.createWXAPI(sContext, SysConstants.weChatAppId, false);

//        if(!NotificationsUtils.isNotificationEnabled(sContext)){
//            NotificationsUtils.toSetting(sContext);
//        }
//        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);
//        PushManager.getInstance().initialize(this.getApplicationContext(), MyPushService.class);
//        Vitamio.initialize(sContext);


        // 将该app注册到微信
//        msgApi.registerApp(SysConstants.weChatAppId);


//        TCAgent.LOG_ON=true;
//        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
//        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
//        TCAgent.init(this, "C8751B1CE3EF41F6871079E4886C21E3", "TalkingData");
//        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
//        TCAgent.setReportUncaughtExceptions(true);

        //        new XclModel().requestaccessToken();
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

//    public static void initTtsUtils(Activity activity) {
//        SpeechUtility.createUtility(activity, SpeechConstant.APPID + "=5922ad0d");
//    }

//    private void initLocation(Context sContext) {
//        LocationModel mModel = new LocationModel(sContext);
//        mModel.statr();
//    }
//
//    private void initSign() {
//        FileUtil.copy(this, SignUtils.FileName, getFilesDir().getAbsolutePath(), SignUtils.FileName);
//
//    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    class MyHandler implements UncaughtExceptionHandler {
        // 一旦某个异常未被捕获,就走到此方法中
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            try {
                //  Thread.sleep(2000);
                CrashReport.postCatchedException(ex, thread);
                Log.e(TAG, "error : " + ex.getCause().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public static AppApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return sContext;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void applicationExit() {
        System.exit(0);
    }

    //初始化图片加载工厂
    private void initImageFactory(Context context) {

        DisplayImageOptions defaultOptions = buildImageOptions(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .threadPoolSize(5)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//线程优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//文件缓存到本地命名
                .memoryCache(new LRULimitedMemoryCache(getMaxSize() / 8))//缓存大小超过指定值时,删除最近最少使用的
                .memoryCacheSize(getMaxSize() / 8)// 内存缓存的最大值
                .diskCache(new UnlimitedDiskCache(new File(imgPath)))
                .diskCacheSize(50 * 1024 * 1024)//50 Mb sd卡(本地)缓存的最大值
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 创建图片参数
     * 初始化显示配置
     */
    private DisplayImageOptions buildImageOptions(Context mContext) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher).considerExifParams(true)
                .cacheInMemory(true).cacheOnDisk(true).build();

        return defaultOptions;
    }

    public int getMaxSize() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
        return maxMemory;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }


    //

    /**
     * 设置greenDao
     */
//    private void setDatabase() {
//        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
//        db = mHelper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }
}
