package com.xiaoli.library.task;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import com.xiaoli.library.C;
import com.xiaoli.library.R;
import com.xiaoli.library.utils.DateUtils;

/**
 * 轮询service
 *  xiaokx
 *  hioyes@qq.com
 *  2014-11-6
 */
public class PollingService extends Service {

    public static final String ACTION = "com.xiaoli.library.task.PollingService";

    /**
     * 通知栏icon
     */
    public static int ICON = 0;

    private Notification mNotification;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service","onCreate");
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("service","onStart");
        new PollingThread().start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("service","onDestroy");
    }

    private void initNotifiManager() {
        //消息通知栏处理
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = ICON;
        mNotification = new Notification();
        mNotification.icon = icon;
        mNotification.tickerText = "有新消息";
        mNotification.defaults |= Notification.DEFAULT_SOUND;
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    }
    //弹出Notification
    private void showNotification() {
        Log.e("service","showNotification");
    }
    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     */
    class PollingThread extends Thread {
        @Override
        public void run() {
            updateTask();
        }
    }

    private void updateTask(){

    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    private int getLocalVerCode() {
        int verCode = -1;
        try {
            verCode = C.mCurrentActivity.getPackageManager().getPackageInfo("com.lubaocar.analytics", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (RuntimeException e) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return verCode;
    }

}
