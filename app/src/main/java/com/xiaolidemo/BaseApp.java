package com.xiaolidemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.xiaoli.library.C;
import com.xiaoli.library.utils.CrashHandler;
import com.xiaoli.library.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 应用全局对象
 * xiaokx
 * hioyes@qq.com
 * 2016-6-17
 */
public class BaseApp extends Application {

    // ===========常量==========
    private static final String TAG = "BaseApp";


    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext(), C.ROOT_CATALOG+"log/");
        C.init("com.xiaolidemo", Environment.getExternalStorageDirectory().getAbsolutePath()+"/xiaolidemo/",true);
        C.NONE_CHEECK_VERSION.add("SplashActivity");
        C.NONE_CHEECK_VERSION.add("GuideActivity");
        if (Config.PRODUCTION_ENVIRONMENT == 2) {
            //准生产
            C.CHECK_VERSION_URL = "http://app.lubaocar.com/android/analytics/quasi/version.js";
        } else if (Config.PRODUCTION_ENVIRONMENT == 3) {
            //生产
            C.CHECK_VERSION_URL = "http://app.lubaocar.com/android/analytics/production/version.js";
        }
        C.DLG_UPDATE = R.layout.update;

        copyDbFile();
    }


    /**
     * 数据库拷贝
     */
    private void copyDbFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                File dir = new File(Config.DB_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                copyDdFile("lbdetect.db", Config.DB_PATH, R.raw.lbdetect, getApplicationContext());
            }
        }).start();
    }

    /**
     * 将资源文件复制到sd卡
     * @param targetFileName
     * @param targetPath
     * @param sourceId
     * @param context
     */
    public static void copyDdFile(String targetFileName, String targetPath, int sourceId,Context context) {
        try {
            File dir = new File(targetPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!(new File(targetPath+targetFileName)).exists()) {
                InputStream is = context.getResources().openRawResource(sourceId);
                FileOutputStream fos = new FileOutputStream(targetPath+targetFileName);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
